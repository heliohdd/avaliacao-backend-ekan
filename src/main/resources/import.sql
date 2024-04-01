insert into beneficiario (id, nome, telefone, data_nascimento, data_inclusao, data_atualizacao) values (1, 'João', '985790-1324', '29/03/1980', utc_timestamp, utc_timestamp);
insert into beneficiario (id, nome, telefone, data_nascimento, data_inclusao, data_atualizacao) values (2, 'Maria', '857901-3249', '02/06/1977', utc_timestamp, utc_timestamp);
insert into beneficiario (id, nome, telefone, data_nascimento, data_inclusao, data_atualizacao) values (3, 'Pedro', '985790-1324', '21/04/1991', utc_timestamp, utc_timestamp);

insert into documento (id, tipo_documento, descricao, data_inclusao, data_atualizacao, beneficiario_id) values (1, 'RG', 'Normal', utc_timestamp, utc_timestamp, 1);
insert into documento (id, tipo_documento, descricao, data_inclusao, data_atualizacao, beneficiario_id) values (2, 'CPF', 'Estrangeiro', utc_timestamp, utc_timestamp, 1);
insert into documento (id, tipo_documento, descricao, data_inclusao, data_atualizacao, beneficiario_id) values (3, 'Passaporte', 'Inglês', utc_timestamp, utc_timestamp, 2);