package com.ismael.manageproductsprovider;

import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ismael.manageproductsprovider.adapter.ProductAdapter;
import com.ismael.manageproductsprovider.interfaces.IProduct;
import com.ismael.manageproductsprovider.interfaces.IProductPresenter;
import com.ismael.manageproductsprovider.model.Product;
import com.ismael.manageproductsprovider.presenter.ProductPresenterImpl;
import com.ismael.manageproductsprovider.utils.SelectionUtils;

import android.widget.AbsListView;

import java.util.ArrayList;


/**
 * Class which shows the product list
 *
 * @author José Antonio Barranquero Fernández
 * @version 1.0
 */
public class MultiListProductFragment extends Fragment implements IProduct, IProductPresenter.View {

    private ProductAdapter mAdapter;
    private ListView mListProduct;
    private FloatingActionButton mFabAdd;
    private ListProductListener mCallback;
    private TextView mTxvNoData;
    private ProductPresenterImpl mPresenter;
    private static MultiListProductFragment f;
    private int myStatusBarColor;
    private int myCont = 0;
    ArrayList<Integer> deletePos;

    @Override
    public void setProductCursor(Cursor cursor) {
        if(cursor != null)
         mAdapter.swapCursor(cursor);
    }

    public interface ListProductListener {
        void showManageProduct(Bundle bundle);
    }

    public static MultiListProductFragment getInstance(Bundle args){

        f = new MultiListProductFragment();
        f.setArguments(args);
        return  f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new ProductAdapter(getContext(), null, 1);
        mPresenter = new ProductPresenterImpl(this);
        deletePos = new ArrayList();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (ListProductListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(e.getMessage() + " activity must implement ListProductListener interface");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getAllProducts(mAdapter);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mAdapter = null;
        mPresenter = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_list_product, container, false);

        mListProduct = (ListView) rootView.findViewById(android.R.id.list);

        mTxvNoData = (TextView)rootView.findViewById(android.R.id.empty);

        //registerForContextMenu(mListProduct);

        mFabAdd = (FloatingActionButton) rootView.findViewById(R.id.fabAddProduct);
        mFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.showManageProduct(null);
            }
        });

        Product tmp;
        if(f.getArguments() != null) {
            Bundle b = f.getArguments();
            if(b.getString("action") == PRODUCT_KEY) {
                tmp = b.getParcelable(PRODUCT_KEY);
                mPresenter.addProduct(tmp);
            }
            else if(b.getString("action") == OLD_KEY){
                int idOldProduct = ((Product) b.getParcelable(OLD_KEY)).getID();
                tmp = b.getParcelable(PRODUCT_KEY);
                mPresenter.updateProduct(idOldProduct, tmp);
            }
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListProduct.setAdapter(mAdapter);
        mListProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(PRODUCT_KEY, (Product) parent.getItemAtPosition(position));
                mCallback.showManageProduct(bundle);
            }
        });


        mListProduct.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if (checked) {
                    myCont++;
                    if(!deletePos.contains(position))
                        deletePos.add(position);
                    //presenter.setNewSelection(position, checked);
                } else {
                    myCont--;
                    //presenter.removeSelection(position);
                }
                mode.setTitle(Integer.toString(myCont));
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.menu_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    myStatusBarColor = getActivity().getWindow().getStatusBarColor();
                    getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorError));//ContextCompat.getColor(getActivity(), R.color.colorError));
                }
        /*for (View v : listView) {
            v.setVisibility(View.INVISIBLE);
        }*/
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete_product:
                        for (int pos : deletePos) {
                            mPresenter.deleteProduct(((Product)(mAdapter.getItem(pos))).getID());
                        }
                        showMessageDelete(null);

                        break;
                }
                mode.finish();
                return true;
            }



            @Override
            public void onDestroyActionMode(ActionMode mode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getActivity().getWindow().setStatusBarColor(myStatusBarColor);

                //preseneter.clearSelection();
        /*for (View v :
                listView) {
            v.setVisibility(View.VISIBLE);
        }*/
                myCont = 0;
            }
        }});


        mListProduct.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        final SelectionUtils selectionUtils = new SelectionUtils(mListProduct.getCheckedItemPositions());

        mListProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectionUtils.setSparseBoolean(mListProduct.getCheckedItemPositions());
                mListProduct.setItemChecked(position, !selectionUtils.isPositionChecker(position));
                return true;
            }

        });

    }


    private void hideList(boolean hide) {
        mListProduct.setVisibility(hide? View.GONE : View.VISIBLE);
        mTxvNoData.setVisibility(hide? View.VISIBLE : View.GONE);
    }

    public void showEmptyState(boolean show) {
        hideList(show);
    }


    public void showMessageDelete(final Product product) {
        Snackbar.make(getView(), "Producto eliminado", Snackbar.LENGTH_SHORT)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //    mPresenter.addProduct(product);
                    }
                }).show();

        // SetCallback (calling a SnackBar callback method, even if the SnackBar has been deleted by Swiping
        /*.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if ((event == DISMISS_EVENT_TIMEOUT) || (event == DISMISS_EVENT_SWIPE) || event == DISMISS_EVENT_MANUAL || event == DISMISS_EVENT_CONSECUTIVE) {
                //if (event != DISMISS_EVENT_ACTION) {
                    mPresenter.deleteFinallyProduct(product);
                }
            }
        }).show();*/
    }
}
