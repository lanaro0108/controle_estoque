# Especificações do Projeto: Estoque & Patrimônio SENAI-SP

## Requisitos Funcionais

### Acesso e Segurança

RF-01: Cadastro de usuários: O funcionário cria sua conta, mas o sistema só libera se o NIF e o Nome baterem com a "lista de autorizados" (pré-cadastro).

RF-02: Login com NIF: Só entra quem tem NIF e senha cadastrados.

RF-03: Controle de conta: Opção para deixar um usuário "inativo" (bloqueia o acesso sem precisar deletar o histórico de quem ele é).

RF-04: Logout: Botão para encerrar a sessão com segurança.

### Controle de Estoque (Consumíveis)
RF-05: Catálogo de Materiais: Cadastro de itens (componentes, fios, reagentes) com nome, quantidade e categoria (ex: Elétrica, Mecânica).

RF-06: Entradas e Saídas: Registro de quando chega material novo ou quando sai para uma aula. O sistema deve calcular o saldo atualizado sozinho.

RF-07: Histórico de Uso: Lista que mostra o "quem, quando e quanto" de cada movimentação.

RF-08: Alerta de Estoque Baixo: Destacar visualmente os itens que estão quase acabando.

### Gestão de Patrimônio (Equipamentos)
RF-09: Cadastro de Ativos: Registro de bens fixos (tornos, PCs, bancadas) usando o número de inventário do SENAI.

RF-10: Localização: Informar em qual sala ou laboratório o equipamento está fixado.

### Interface e Backend
RF-11: Dashboard: Uma tela principal com um resumo rápido (ex: quantos itens saíram hoje, total de ativos).

RF-12: API REST: Backend em Spring Boot organizado para servir os dados para a interface.

RF-13: Banco de Dados: Guardar tudo com segurança, garantindo que as tabelas se conversem (Material ligado com Categoria, etc).

## Requisitos Não Funcionais
RNF-01: Visual SENAI: Usar as cores padrão (Vermelho, Branco e Cinza) e o logo oficial do SENAI-SP.

RNF-02: Tela Responsiva: O sistema tem que funcionar bem tanto no computador quanto no tablet/celular.

RNF-03: Facilidade de Uso: Interface limpa e intuitiva, feita para quem está na correria do laboratório.

RNF-04: Segurança: As senhas devem ser salvas com criptografia (nada de texto limpo no banco!).

RNF-05: Travas de Segurança: Não deixar excluir uma categoria se ela ainda tiver materiais dentro.
