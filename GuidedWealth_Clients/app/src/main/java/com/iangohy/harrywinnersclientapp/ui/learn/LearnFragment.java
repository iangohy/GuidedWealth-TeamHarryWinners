package com.iangohy.harrywinnersclientapp.ui.learn;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.Query;
import com.iangohy.harrywinnersclientapp.FirebaseUtil;
import com.iangohy.harrywinnersclientapp.R;
import com.iangohy.harrywinnersclientapp.ReviewActivity;
import com.iangohy.harrywinnersclientapp.adapter.ArticleAdapter;
import com.iangohy.harrywinnersclientapp.adapter.BankerProfileAdapter;
import com.iangohy.harrywinnersclientapp.databinding.FragmentLearnBinding;
import com.iangohy.harrywinnersclientapp.model.Article;
import com.iangohy.harrywinnersclientapp.model.Banker;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;

public class LearnFragment extends Fragment {

    private FragmentLearnBinding binding;
    private ArticleAdapter suggestedArticleAdapter;
    private ArticleAdapter topArticleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        LearnViewModel notificationsViewModel =
//                new ViewModelProvider(this).get(LearnViewModel.class);

        binding = FragmentLearnBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textNotifications;
//        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        // Add articles
//        String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce pellentesque, dolor a finibus consequat, lacus dui condimentum nulla, ut mollis lorem erat ut nisi. Curabitur faucibus nibh nec nulla viverra, et bibendum est aliquet. Mauris euismod, elit ultricies tincidunt mollis, ipsum lectus venenatis mi, non consequat dui magna a orci. Sed at dui dui. Nullam tristique sit amet diam in imperdiet. Integer diam tortor, porttitor vel elit eget, condimentum posuere ante. Etiam est est, venenatis quis viverra ut, tempor ac justo. Maecenas venenatis orci eget nunc lobortis mollis id in dolor. Morbi id vulputate quam, nec tincidunt arcu. Morbi bibendum condimentum egestas. Sed vestibulum porttitor tellus, vitae iaculis dolor semper vitae. Nunc eu molestie nisl. In mollis nec quam vel tempus. Aenean pretium, nunc a dignissim vehicula, orci est sagittis urna, at fringilla ex tellus eget diam. Nulla euismod turpis vel ligula venenatis, at pellentesque est tempor. Cras sit amet fringilla eros.\n" +
//                "\n" +
//                "Nulla tincidunt vel tortor a fringilla. Praesent pharetra erat est, eget sollicitudin tortor vehicula sed. In at magna eu purus rhoncus laoreet a eget eros. Ut tempus est nec dui porttitor mollis. Sed quis dapibus ligula. Vivamus venenatis, lacus non sagittis eleifend, mauris justo scelerisque justo, at placerat leo sapien sit amet dui. In mollis dui augue, ut consequat lorem commodo eu. Aenean nec sodales mauris, sit amet consectetur nulla. Morbi dictum risus eu mattis tristique. Suspendisse sit amet felis quis orci sollicitudin mollis. Etiam velit augue, feugiat eu aliquet vel, luctus in sem.\n" +
//                "\n" +
//                "Nullam eget fringilla justo. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam et volutpat velit. Phasellus non urna porttitor, sagittis libero eu, rutrum lacus. Integer ex justo, maximus viverra augue facilisis, maximus cursus justo. Morbi vel venenatis mi. Morbi in volutpat quam, eu finibus felis.\n" +
//                "\n" +
//                "Sed ac egestas felis, a ultricies ligula. Proin consectetur nibh sit amet pharetra facilisis. Nulla facilisi. Fusce ligula tortor, fermentum quis ex et, consequat tincidunt orci. Ut luctus quis magna in tristique. Cras a laoreet orci. Aliquam sodales justo porta sollicitudin pharetra.\n" +
//                "\n" +
//                "Ut placerat iaculis elementum. Duis ultricies, est quis pulvinar aliquet, sem diam porta diam, condimentum mollis risus augue sit amet nisl. Nulla facilisi. In pretium mauris id nibh consectetur ultrices. Phasellus dignissim arcu non eros tincidunt varius. Sed sit amet dui in dolor varius faucibus vel vitae ipsum. Donec rhoncus nisl vel neque laoreet cursus feugiat a mauris. Sed mauris sem, auctor eu aliquam quis, lobortis sed purus. Morbi eu facilisis eros. Proin semper mi nulla, ac semper est pretium nec. Donec vitae ullamcorper mauris. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Nunc in leo maximus, convallis lectus ac, placerat odio. Pellentesque dignissim urna sed neque consequat tristique. Quisque ac sem malesuada, porttitor tortor aliquet, ullamcorper tortor. Pellentesque hendrerit venenatis libero sed fermentum.";
//
//        Banker banker1 = new Banker(
//                "Tom Tan",
//                "Vice-President",
//                "I am a good banker",
//                "https://st.depositphotos.com/1597387/1984/i/600/depositphotos_19841901-stock-photo-asian-young-business-man-close.jpg",
//                5280);
//        Banker banker2 = new Banker("John Capita", "Associate", "Guiding others to grow their financial portfolio is my calling.", "https://www.moneymozart.com/wp-content/uploads/2016/02/how-to-become-an-investment-banker.jpg", 280);
//        Article article1 = new Article(50, 538, 22, new Date(), "Why diversifying your portfolio is crucial!", loremIpsum, banker1);
//        Article article2 = new Article(
//                500,
//                912,
//                211,
//                new Date(),
//                "What are ETFs?",
//                loremIpsum,
//                banker2
//        );
//
//        ArrayList<Article> suggestArticles = new ArrayList<>();
//        suggestArticles.add(article1);
//
//        ArrayList<Article> topArticles = new ArrayList<>();
//        topArticles.add(article2);
//        topArticles.add(article1);

        // Suggestion recycler
        RecyclerView suggestRecycler = binding.suggestedRecycler;
        suggestRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestedArticleAdapter = new ArticleAdapter(FirebaseUtil.getArticleCollection().orderBy("date").limit(1));
        suggestRecycler.setAdapter(suggestedArticleAdapter);

        // Top recycler
        RecyclerView topRecycler = binding.topRecycler;
        topRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        topArticleAdapter = new ArticleAdapter(FirebaseUtil.getArticleCollection().orderBy("upvotes", Query.Direction.DESCENDING).limit(2));
        topRecycler.setAdapter(topArticleAdapter);

        return root;
    }

    @Override
    public void onStart() {
        Log.i("LearnFragment", "ONSTART");
        super.onStart();
        if (suggestedArticleAdapter != null) {
            suggestedArticleAdapter.startListening();
        }
        if (topArticleAdapter != null) {
            topArticleAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (suggestedArticleAdapter != null) {
            suggestedArticleAdapter.stopListening();
        }
        if (topArticleAdapter != null) {
            topArticleAdapter.stopListening();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}