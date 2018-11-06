package com.example.bukanijang.pokemon_kotlin


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bukanijang.pokemon_kotlin.Adapter.PokemonListAdapter
import com.example.bukanijang.pokemon_kotlin.Common.Common
import com.example.bukanijang.pokemon_kotlin.Common.ItemOffsetDecoration
import com.example.bukanijang.pokemon_kotlin.Retrofit.IPokemonList
import com.example.bukanijang.pokemon_kotlin.Retrofit.RetrofitClient
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_pokemon_list.*


class PokemonList : Fragment() {

    internal var compositeDisposable = CompositeDisposable()
    internal var  iPokemonList:IPokemonList

    init {
        val retrofit = RetrofitClient.instance
        iPokemonList = retrofit.create(IPokemonList::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        pokemon_recycleview.setHasFixedSize(true)
        pokemon_recycleview.layoutManager = GridLayoutManager(activity, 2)
        val itemDecoration = ItemOffsetDecoration(activity!!,R.dimen.spacing)
        pokemon_recycleview.addItemDecoration(itemDecoration)

        fetchData()

        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{ pokemonDex ->
                    Common.pokemonList = pokemonDex.pokemon!!
                    val adapter = PokemonListAdapter(activity!!,Common.pokemonList)

                    pokemon_recycleview.adapter = adapter
                }
        );
    }


}
