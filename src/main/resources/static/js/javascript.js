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

    let valid = true;

    inputs.forEach(input => {
        if (!input.checkValidity()) {
            valid = false;
            input.reportValidity();
        }
    });

    if (valid) {
        etapa1.style.display = "none";
        document.getElementById("etapa2").style.display = "block";

    } else {
        Swal.fire({
            icon: 'warning',
            title: 'Atenção!',
            text: 'Por favor, preencha todos os campos corretamente.'
        });
    }
}

function voltarEtapa() {
    document.getElementById("etapa2").style.display = "none";
    document.getElementById("etapa1").style.display = "block";
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

// VERIFICA CAMPOS DA ETAPA 2 / TIPO DE COMERCIO E SE O NOME DO COMERCIO EXISTE

// O NOME DO COMERCIO FAZ UMA REQUISIÇÃO: > AJAX > CONTROLLER > SERVICE > BANCO DE DADOS
//PARA NÃO PRECISAR RECARREGAR A PAGINA
function verificarNomeComercio() {
    const etapa2 = document.getElementById("etapa2");
    const inputs = etapa2.querySelectorAll("input, select");
    let camposInvalidos = [];

    // Verifica se o "Tipo de Comércio" foi selecionado
    const tipoComercio = document.querySelector('[name="tipoComercioLogista"]').value;
    if (!tipoComercio) {
        camposInvalidos.push('Tipo de Comércio');
    }

    // Valida todos os outros inputs e select
    inputs.forEach(input => {
        if (!input.checkValidity()) {
            camposInvalidos.push(input.previousElementSibling ? input.previousElementSibling.innerText : input.name);
        }
    });

    // Se houver campos inválidos, mostra o alerta
    if (camposInvalidos.length > 0) {
        Swal.fire({
            icon: 'warning',
            title: 'Atenção!',
            text: 'Por favor, preencha todos os campos corretamente.'
        });
        return;
    }

    const nomeComercio = document.querySelector('[name="nomeComercioLogista"]').value;

    // Verifica se o nome do comércio já existe via AJAX
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
            Swal.fire({
                icon: 'error',
                title: 'Nome em uso!',
                text: 'Esse nome de comércio já está em uso. Por favor, escolha outro.'
            });
        } else {
            const formCadastro = document.querySelector('#cadastrarModal form');
            formCadastro.submit();
        }
    })

    .catch(error => {
        console.error('Erro na verificação do nome do comércio:', error);
        Swal.fire({
            icon: 'error',
            title: 'Erro!',
            text: 'Não foi possível verificar o nome do comércio. Tente novamente mais tarde.'
        });
    });
}