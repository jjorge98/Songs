package br.iesb.songs.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.iesb.songs.interactor.LoginInteractor

class LoginViewModel(val app: Application) : AndroidViewModel(app) {
    //variável que chama o interactor
    val interactor = LoginInteractor(app.applicationContext)

    fun signOut() {
        interactor.signOut()
    }

    //função de recuperar senha que recebe o email e um callback
    fun recuperarSenha(email: String, callback: (result: Array<String>) -> Unit) {
        //como não tem nenhuma verificação responsável pela view model, já chama a função do interactor
        interactor.recuperarSenha(email) { result ->
            //após ter recebido o resultado da interactor, ele faz as verificações necessárias e
            // mostra a mensagem referente ao resultado ao usuário
            if (result == "OK") {
                val resultado = arrayOf("OK", "E-mail de recuperação de senha enviado com sucesso!")
                callback(resultado)
            } else if (result == "VAZIO") {
                val resultado = arrayOf("ERROR", "Por favor, preencha todos os campos!")
                callback(resultado)
            } else if (result == null) {
                val resultado = arrayOf(
                    "ERROR",
                    "Algo deu errado ao enviar o e-mail de recuperação de senha. Contate o adm do sistema!"
                )
                callback(resultado)
            } else {
                val resultado = arrayOf("ERROR", result)
                callback(resultado)
            }
        }
    }

    //função de login que recebe o email, a senha e um callback
    fun login(email: String, password: String, callback: (result: Array<String>) -> Unit) {
        //como não tem nenhuma verificação responsável pela view model, já chama a função do interactor
        interactor.login(email, password) { result ->
            //após ter recebido o resultado da interactor, ele faz as verificações necessárias e
            // mostra a mensagem referente ao resultado ao usuário
            if (result == "OK") {
                val resultado = arrayOf("OK", "Login efetuado com sucesso!")
                callback(resultado)
            } else if (result == "VAZIO") {
                val resultado = arrayOf("ERROR", "Por favor, preencha todos os campos!")
                callback(resultado)
            } else if (result == null) {
                val resultado = arrayOf(
                    "ERROR",
                    "Algo deu errado ao fazer o login. Contate o adm do sistema!"
                )
                callback(resultado)
            } else if ("badly" in result) {
                val resultado = arrayOf(
                    "ERROR",
                    "O formato do e-mail está errado. Por favor, verifique e tente novamente!"
                )
                callback(resultado)
            } else if ("record corresponding" in result) {
                val resultado = arrayOf(
                    "ERROR",
                    "O e-mail informado não está cadastrado no sistema. Por favor contate o adm do sistema para cadastro!"
                )
                callback(resultado)
            } else if ("password is invalid" in result) {
                val resultado = arrayOf(
                    "ERROR",
                    "Senha inválida. Por favor, verifique e tente novamente!"
                )
                callback(resultado)
            } else {
                val resultado = arrayOf("ERROR", result)
                callback(resultado)
            }
        }
    }

    //função de login que recebe o email, a senha, a confirmação de senha e um callback
    fun register(
        email: String,
        password: String,
        confirmPassword: String,
        callback: (result: Array<String>) -> Unit
    ) {
        //como não tem nenhuma verificação responsável pela view model, já chama a função do interactor
        interactor.register(email, password, confirmPassword) { result ->
            //após ter recebido o resultado da interactor, ele faz as verificações necessárias e
            // mostra a mensagem referente ao resultado ao usuário
            if (result == "OK") {
                val resultado = arrayOf("OK", "Cadastro efetuado com sucesso!")
                callback(resultado)
            } else if (result == "VAZIO") {
                val resultado = arrayOf("ERROR", "Por favor, preencha todos os campos!")
                callback(resultado)
            } else if (result == "SENHAS") {
                val resultado = arrayOf("ERROR", "As senhas informadas estão diferentes!")
                callback(resultado)
            } else if (result == "SENHA") {
                val resultado = arrayOf("ERROR", "A senha deve ter no mínimo 6 caracteres!")
                callback(resultado)
            } else if (result == null) {
                val resultado = arrayOf(
                    "ERROR",
                    "Algo deu errado ao fazer o cadastro. Contate o adm do sistema!"
                )
                callback(resultado)
            } else {
                val resultado = arrayOf("ERROR", result)
                callback(resultado)
            }
        }
    }

    fun verifyLogin(callback: (result: Int) -> Unit) {
        interactor.verifyLogin(callback)
    }

    fun updateName(name: String, callback: (String) -> Unit) {
        interactor.updateName(name) { response ->
            if (response == "OK") {
                val text = "Nome salvo com sucesso!"
                callback(text)
            } else if (response == "EMPTY") {
                val text = "Por favor, preencha seu nome!"
                callback(text)
            } else {
                val text = response ?: "Ocorreu um erro ao salvar seu nome. Por favor, tente novamente mais tarde"
                callback(text)
            }
        }
    }
}
