package com.ismael.manageproductsprovider;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ismael.manageproductsprovider.adapter.CategoriesAdapter;
import com.ismael.manageproductsprovider.interfaces.ICategoryPresenter;
import com.ismael.manageproductsprovider.presenter.CategoryPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryListFragment extends Fragment implements ICategoryPresenter.View{
    static CategoryListFragment catF;
    ListView listaCategorias;
    CategoriesAdapter adapterCat;
    CategoryListListener mListener;
    private CategoryPresenterImpl presenter;
    TextView noData;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void setCursorCategory(Cursor cursor) {
        if(cursor != null)
         adapterCat.swapCursor(cursor);
    }


    public interface CategoryListListener{
        void showListCategories();
    }

    public static CategoryListFragment getInstance(Bundle b){
        if(catF == null) {
            catF = new CategoryListFragment();
            catF.setArguments(b);
        }
        return catF;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterCat = new CategoriesAdapter(getContext(), null, 1);
        presenter = new CategoryPresenterImpl(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_category_list, container, false);
        listaCategorias = (ListView) rootView.findViewById(R.id.categoryList);
        noData = (TextView) rootView.findViewById(R.id.emptyCat);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CategoryListListener) {
            mListener = (CategoryListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaCategorias.setAdapter(adapterCat);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getAllCategories(adapterCat);
    }
}
