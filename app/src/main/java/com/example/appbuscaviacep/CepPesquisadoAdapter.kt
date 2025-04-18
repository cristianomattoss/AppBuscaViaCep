package com.example.appbuscaviacep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.appbuscaviacep.database.model.CepPesquisado
import com.example.appbuscaviacep.databinding.CardViewBinding

class CepPesquisadoAdapter : RecyclerView.Adapter<CepPesquisadoAdapter.CepViewHolder>() {

    private var listaCeps = mutableListOf<CepPesquisado>()

    fun carregarLista(lista: MutableList<CepPesquisado>) {
        listaCeps = lista
        notifyDataSetChanged()
    }

    fun adicionarPesquisa(cepPesquisado: CepPesquisado) {
        if(listaCeps.size == 10) {
            listaCeps.removeAt(listaCeps.size - 1)
            notifyItemRemoved(listaCeps.size)
            listaCeps.add(0, cepPesquisado)
            notifyItemInserted(0)
        } else {
            listaCeps.add(0, cepPesquisado)
            notifyItemInserted(0)
        }
    }

    inner class CepViewHolder (
        val binding: CardViewBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CepViewHolder {
        val layoutInflater = LayoutInflater.from(
            parent.context
        )

        val itemView = CardViewBinding.inflate(
            layoutInflater, parent, false
        )

        return CepViewHolder( itemView )
    }

    override fun getItemCount(): Int {
        return listaCeps.size
    }

    override fun onBindViewHolder(holder: CepViewHolder, position: Int) {

        val endereco = listaCeps[position]
        with(holder.binding) {
            cepText.text = endereco.cep
            logradouroText.text = endereco.logradouro
            bairroText.text = endereco.bairro
            cidadeText.text = endereco.cidade
            estadoText.text = endereco.estado
        }
    }
}