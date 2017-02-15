package wb.app.seek.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by W.b on 2017/1/10.
 */
public abstract class BaseFragment extends Fragment {

//  @Nullable
//  @Override
//  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//    View view = inflater.inflate(getContentViewId(), container, false);
//    ButterKnife.bind(this, view);
//    return view;
//  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    initComponents(view);
  }

  protected abstract int getContentViewId();

  protected abstract void initComponents(View view);
}
