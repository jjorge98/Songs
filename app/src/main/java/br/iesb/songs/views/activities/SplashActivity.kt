package br.iesb.songs.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel

//TODO: Remover algum item dos favoritos
//TODO: Arrumar os favoritos. Verificar se já existe no banco
class SplashActivity : AppCompatActivity() {
    private val viewModel: LoginViewModel by lazy{
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changeToLogin()
    }

    private fun changeToLogin(){
        lateinit var intent: Intent
        viewModel.verifyLogin {result ->
            if(result == 0){
                intent = Intent(this, MainInicialActivity::class.java)
            } else{
                intent = Intent(this, FavoriteListActivity::class.java)
            }
        }

        Handler().postDelayed({
            intent.change()
        }, 2000)
    }

    private fun Intent.change(){
        startActivity(this)
        finish()
    }
}
