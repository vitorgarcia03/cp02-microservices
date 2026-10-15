CREATE TABLE dado_coletado (
    dado_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sonda_nome VARCHAR(100) NOT NULL,
    agencia_espacial VARCHAR(100) NOT NULL,
    data_coleta DATE NOT NULL,
    tipo_dado VARCHAR(30) NOT NULL,
    valor_medido DECIMAL(19,4) NOT NULL,
    unidade_medida VARCHAR(100) NOT NULL,
    CONSTRAINT ck_sonda_nome CHECK (CHAR_LENGTH(sonda_nome) >= 3),
    CONSTRAINT ck_agencia_espacial CHECK (CHAR_LENGTH(agencia_espacial) >= 3),
    CONSTRAINT ck_unidade_medida CHECK (CHAR_LENGTH(unidade_medida) >= 3),
    CONSTRAINT ck_tipo_dado CHECK (tipo_dado IN ('TEMPERATURA','PRESSAO','RADIACAO','COMPOSICAO_ATMOSFERICA','VELOCIDADE_VENTO'))
);
