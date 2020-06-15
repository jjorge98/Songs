package br.iesb.songs.views.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.iesb.songs.R
import br.iesb.songs.view_model.LoginViewModel
import br.iesb.songs.views.PrincipalActivity
import kotlinx.android.synthetic.main.fragment_user_name.*

class UserNameFragment(private val type: String) : Fragment() {
    private val viewModelL: LoginViewModel by lazy {
        ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (type != "exist") {
            closeButton.visibility = View.GONE
        } else {
            closeButton.setOnClickListener { dismiss() }
        }

        backUserName.setOnTouchListener { _, _ ->
            val inputMethodManager: InputMethodManager =
                requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
        }

        saveUserName.setOnClickListener { save() }
    }

    private fun save() {
        val name = userName.text.toString()

        viewModelL.updateName(name) { response ->
            Toast.makeText(context, response, Toast.LENGTH_SHORT).show()

            if (response == "Nome salvo com sucesso!") {
                dismiss()
            }
        }
    }

    private fun dismiss() {
        if (type == "doesntExists") {
            val intentLogin = Intent(context, PrincipalActivity::class.java)
            startActivity(intentLogin)
        }

        Handler().postDelayed({
            val manager = activity?.supportFragmentManager

            manager?.findFragmentByTag("userName")?.let { it1 ->
                manager.beginTransaction().remove(
                    it1
                ).commit()
            }
        }, 1000)
    }

}
