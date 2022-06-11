package yaroslavlebid.apps.myhome.ui.home.apartments.current_apartment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import yaroslavlebid.apps.myhome.R
import yaroslavlebid.apps.myhome.data.review.Review
import yaroslavlebid.apps.myhome.databinding.ItemOpenedApartmentBinding
import yaroslavlebid.apps.myhome.databinding.ItemReviewBinding
import yaroslavlebid.apps.myhome.utils.setImageFromUrl

class CurrentApartmentReviewsAdapter(private val reviews: List<Review>) :
    RecyclerView.Adapter<CurrentApartmentReviewsAdapter.CurrentApartmentReviewsViewHolder>() {

    var onLikeClicked: ((String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentApartmentReviewsViewHolder {
        val binding =
            ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrentApartmentReviewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrentApartmentReviewsViewHolder, position: Int) {
        with(holder) {
            reviews[position].apply {
                binding.commentText.text = this.comment
                binding.likesNum.text = this.likes.toString()
                binding.likeButton.setOnClickListener {
                    onLikeClicked?.invoke(this.id)
                    binding.likesNum.text = (this.likes + 1).toString()
                }
                binding.userImage.setImageFromUrl(this.author.photoUrl, applyCircle = true)
                binding.userName.text = this.author.firstName

                if (this.ratingAvg in (0f..5.1f)) binding.star1.setImageResource(R.drawable.grade_filled)
                if (this.ratingAvg in (1.1f..5.1f)) binding.star2.setImageResource(R.drawable.grade_filled)
                if (this.ratingAvg in (2.1f..5.1f)) binding.star3.setImageResource(R.drawable.grade_filled)
                if (this.ratingAvg in (3.1f..5.1f)) binding.star4.setImageResource(R.drawable.grade_filled)
                if (this.ratingAvg in (4.1f..5.1f)) binding.star5.setImageResource(R.drawable.grade_filled)
            }
        }
    }

    override fun getItemCount() = reviews.count()

    inner class CurrentApartmentReviewsViewHolder(val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root)
}