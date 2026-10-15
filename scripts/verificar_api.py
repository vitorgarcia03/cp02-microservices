"""Com a API iniciada, execute: python3 scripts/verificar_api.py [URL_BASE]."""
import json
import sys
from datetime import date, timedelta
from urllib.request import Request, urlopen
from urllib.error import HTTPError
base = (sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8080").rstrip("/")
path = "/api/dados-coletados"
def call(method, endpoint, expected, data=None):
    request = Request(base + endpoint, data=None if data is None else json.dumps(data).encode(),
                      headers={"Content-Type": "application/json"}, method=method)
    try:
        response = urlopen(request, timeout=15)
    except HTTPError as error:
        response = error
    with response:
        body = response.read()
        assert response.status == expected, (method, endpoint, response.status, body)
        return json.loads(body) if body else None
payload = dict(sondaNome="Sonda Teste", agenciaEspacial="Agencia Teste", dataColeta=date.today().isoformat(),
               tipoDado="TEMPERATURA", valorMedido=-60.5, unidadeMedida="graus Celsius")
assert isinstance(call("GET", path, 200), list)
created = call("POST", path, 201, payload)
record_path = path + "/" + str(created["dadoId"])
try:
    assert call("GET", record_path, 200)["sondaNome"] == payload["sondaNome"]
    for field in ("sondaNome", "agenciaEspacial", "unidadeMedida"):
        for invalid in ("ab", "   ", "x"*101):
            call("POST", path, 400, dict(payload, **{field: invalid}))
        for valid in ("abc", "x"*100):
            assert call("PUT", record_path, 200, dict(payload, **{field: valid}))[field] == valid
    for field in payload:
        missing = payload.copy()
        del missing[field]
        call("POST", path, 400, missing)
        call("POST", path, 400, dict(payload, **{field: None}))
    call("POST", path, 400, dict(payload, dataColeta=(date.today()+timedelta(days=1)).isoformat()))
    call("POST", path, 400, dict(payload, tipoDado="INVALIDO"))
    for tipo in ("TEMPERATURA", "PRESSAO", "RADIACAO", "COMPOSICAO_ATMOSFERICA", "VELOCIDADE_VENTO"):
        assert call("PUT", record_path, 200, dict(payload, tipoDado=tipo))["tipoDado"] == tipo
    assert call("GET", "/v3/api-docs", 200)["openapi"].startswith("3.")
finally:
    call("DELETE", record_path, 204)
call("GET", record_path, 404)
call("PUT", record_path, 404, payload)
call("DELETE", record_path, 404)
print("OK: CRUD, limites de tamanho, obrigatoriedade, enum, datas e OpenAPI.")
