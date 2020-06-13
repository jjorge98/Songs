package br.iesb.songs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import br.iesb.songs.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        changetoLogin()
    }

    private fun changetoLogin(){
        val intent = Intent(this, MainActivity::class.java)

        Handler().postDelayed({
            intent.change()
        }, 2000)
    }
    private fun Intent.change(){
        startActivity(this)
        finish()
    }
}
