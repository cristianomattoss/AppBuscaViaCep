package com.example.appbuscaviacep

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appbuscaviacep.api.InterfaceEndereco
import com.example.appbuscaviacep.api.RetrofitHelper
import com.example.appbuscaviacep.api.model.Endereco
import com.example.appbuscaviacep.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val enderecoAPI by lazy {
        RetrofitHelper.retrofit.create(InterfaceEndereco::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buscarButton.setOnClickListener {
            limparCampos()
            CoroutineScope(Dispatchers.IO).launch {
                val cep = binding.cepInput.text.toString()
                verificarCep(cep)
            }
        }

    }

    private fun limparCampos() {
        with(binding){
            logradouroText.text = getString(R.string.logradouro, "")
            bairroText.text = getString(R.string.bairro, "")
            cidadeText.text = getString(R.string.cidade, "")
            estadoText.text = getString(R.string.estado, "")
        }
    }

    private suspend fun verificarCep(cep: String) {
        if (cep.length == 8 && cep.all { it.isDigit() }) {
            buscarEndereco(cep)
        }
        else {
            withContext(Dispatchers.Main) {
                binding.cepInput.text?.clear()
                Toast.makeText(this@MainActivity, "CEP inválido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun buscarEndereco(cep: String) {
        val retorno: Response<Endereco>?

        try {
            retorno = enderecoAPI.buscarEndereco(cep)
            val endereco = retorno.body()

            withContext(Dispatchers.Main) {
                if(endereco != null && retorno.isSuccessful && !verificarSeDadosSaoInvalidos(endereco)) {
                    atribuirValor(endereco)
                }
                else {
                    Toast.makeText(this@MainActivity, "Dados não encontrados", Toast.LENGTH_SHORT).show()
                }
            }

        }catch(e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Erro ao capturar dados", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun verificarSeDadosSaoInvalidos(endereco: Endereco): Boolean {
        return endereco.logradouro.isNullOrBlank() &&
                endereco.bairro.isNullOrBlank() &&
                endereco.cidade.isNullOrBlank() &&
                endereco.estado.isNullOrBlank()
    }

    private fun atribuirValor(endereco: Endereco) {
        with(binding) {
            logradouroText.text = getString(R.string.logradouro, verificarValor(endereco.logradouro))
            bairroText.text = getString(R.string.bairro, verificarValor(endereco.bairro))
            cidadeText.text = getString(R.string.cidade, verificarValor(endereco.cidade))
            estadoText.text = getString(R.string.estado, verificarValor(endereco.estado))
        }
    }

    private fun verificarValor(valor: String?): String {
        return if (valor.isNullOrBlank()) getString(R.string.invalido) else valor
    }
}