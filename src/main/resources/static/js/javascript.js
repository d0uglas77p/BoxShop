// ABRIR MODAL LOGIN
function openModalLogin() {
    document.getElementById("loginModal").style.display = "block";
}

function closeModalLogin() {
    document.getElementById("loginModal").style.display = "none";
}

// ABRIR MODAL RECUPERAR CONTA
function openModalRecuperarConta() {
    document.getElementById("recuperarContaModal").style.display = "block";
    closeModalLogin();
}

function closeModalRecuperarConta() {
    document.getElementById("recuperarContaModal").style.display = "none";
    openModalLogin()
}

// ABRIR MODAL CADASTRAR
function openModalCadastrar() {
    document.getElementById("cadastrarModal").style.display = "block";
}

function closeModalCadastrar() {
    document.getElementById("cadastrarModal").style.display = "none";
}

// FECHAR MODAIS AO CLICAR FORA DELE
window.onclick = function (event) {
    let loginModal = document.getElementById("loginModal");
    let recuperarContaModal = document.getElementById("recuperarContaModal");
    let cadastrarModal = document.getElementById("cadastrarModal");

    if (event.target === recuperarContaModal) {
        closeModalRecuperarConta();
    }

    if (event.target === loginModal) {
        closeModalLogin();
    }

    if (event.target === cadastrarModal) {
        closeModalCadastrar();
    }
};

// ESTAPAS DE CADASTRO
function proximaEtapa() {
    const etapa1 = document.getElementById("etapa1");
    const inputs = etapa1.querySelectorAll("input");
    const mensagemErro = document.getElementById("mensagemErro");

    let valid = true;

    inputs.forEach(input => {
        if (!input.checkValidity()) {
            valid = false;
            input.reportValidity();
        }
    });

    if (valid) {
        mensagemErro.style.display = "none";
        etapa1.style.display = "none";
        document.getElementById("etapa2").style.display = "block";
    } else {
        mensagemErro.style.display = "block";
    }
}

function voltarEtapa() {
    document.getElementById("etapa2").style.display = "none";
    document.getElementById("etapa1").style.display = "block";
    document.getElementById("mensagemErro").style.display = "none";
}

// FORMATAÇÃO DE TELEFONE
function formatarTelefone(input) {
    var telefone = input.value.replace(/\D/g, '');
    telefone = telefone.replace(/(\d{2})(\d{5})(\d{4})/, '($1) $2-$3');
    input.value = telefone;
}

document.getElementById("telefone-cadastro").addEventListener("input", function () {
    var telefone = this.value.replace(/\D/g, '');

    if (telefone.length !== 11) {
        this.setCustomValidity("Número inválido.");
    } else {
        this.setCustomValidity("");
    }
});

// FORMATAÇÃO DE CPF
function formatarCPF(input) {
    let cpf = input.value.replace(/\D/g, '');

    if (cpf.length > 11) cpf = cpf.slice(0, 11);

    // Aplica a máscara
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

    input.value = cpf;
}

const cpfInput = document.getElementById("cpfLogista");

cpfInput.addEventListener("input", function () {
    formatarCPF(this);

    const cpfNumeros = this.value.replace(/\D/g, '');
    if (cpfNumeros.length !== 11) {
        this.setCustomValidity("CPF inválido.");
    } else {
        this.setCustomValidity("");
    }
});

// VERIFICA COM AJAX (SEM REINICIAR A PAGINA) SE EXISTE UM NOME DE COMERCIO JA CADASTRADO
function verificarNomeComercio() {
    var nomeComercio = document.querySelector('[name="nomeComercioLogista"]').value;
    var mensagemErro = document.getElementById("mensagemErroNomeComercio");

    // Realiza uma requisição AJAX para o backend para verificar se o nome do comércio já existe
    fetch('/verificar-nome-comercio', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ nomeComercio: nomeComercio })
    })
    .then(response => response.json())
    .then(data => {
        if (data.nomeExistente) {
            mensagemErro.style.display = 'block';
            mensagemErro.textContent = 'Esse nome de comércio já está em uso.';

        } else {
            mensagemErro.style.display = 'none';
            document.querySelector('form').submit();
        }
    })
    .catch(error => {
        console.error('Erro ao verificar o nome:', error);
    });
}