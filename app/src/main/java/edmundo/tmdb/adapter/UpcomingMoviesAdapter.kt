package edmundo.tmdb.adapter

import android.app.Activity
import android.arch.paging.PagedListAdapter
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import edmundo.tmdb.BR
import edmundo.tmdb.MovieDetailActivity
import edmundo.tmdb.databinding.UpcomingMoviesItemBinding
import edmundo.tmdb.model.Result
import javax.inject.Inject
import android.support.v4.app.ActivityOptionsCompat
import android.view.View
import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import edmundo.tmdb.utils.StateEnum


class UpcomingMoviesAdapter @Inject constructor(var mFilteredList: MutableList<Result>): PagedListAdapter<Result, RecyclerView.ViewHolder>(MovieCallback) {

    private var state = StateEnum.LOADING

    companion object {
        val MovieCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    override fun onBindViewHolder(baseViewHolder: RecyclerView.ViewHolder, position: Int) {
        (baseViewHolder as? MovieViewHolder)?.bind(getItem(position)!!)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val upcomingMoviesItemBinding = UpcomingMoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(upcomingMoviesItemBinding, parent.context)
    }

    fun addItems(moviesList: List<Result>) {
        mFilteredList.addAll(moviesList)
        notifyDataSetChanged()
    }

    fun clearItems() {
        mFilteredList.clear()
    }


    fun setState(state: StateEnum) {
        this.state = state
        notifyItemChanged(super.getItemCount())
    }


    inner class MovieViewHolder constructor(private val mBinding: UpcomingMoviesItemBinding, context: Context) :
        RecyclerView.ViewHolder(mBinding.root) {

        init {
            mBinding.rootMatchItem.setOnClickListener {
                val p1 = Pair.create(mBinding.ivBackGround as View, "movieImage")
                val p2 = Pair.create(mBinding.tvTitle as View, "movieTitle")
                val p3 = Pair.create(mBinding.tvReleaseDate as View, "movieReleaseDate")

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity, p1, p2, p3)
                context.startActivity(
                    Intent(context, MovieDetailActivity::class.java).putExtra("movieId", getItem(adapterPosition)!!.id),
                    options.toBundle()
                )
            }
        }

        fun bind(movie: Result) {

            mBinding.setVariable(BR.movieItem, movie)

            mBinding.executePendingBindings()
        }

    }
}