package cn.edu.sjzc.student.uiFragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import cn.edu.sjzc.student.R;
import cn.edu.sjzc.student.util.SingleImageTaskUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OneItemFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OneItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneItemFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String SIYUANHUODOND = "/api/newssort/page/findByEnName/global_gongZhuo";
    private ImageView oneitem_frag_one_ib, oneitem_frag_two_ib, oneitem_frag_three_ib, oneitem_frag_four_ib;
    private TextView oneitem_frag_one_text, oneitem_frag_two_text, oneitem_frag_three_text, oneitem_frag_four_text;
    String url = "http://img3.douban.com/spic/s27078194.jpg";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

//    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OneItemFragment newInstance(String param1, String param2) {
        OneItemFragment fragment = new OneItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OneItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one_item, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        Thread loadThread = new Thread(new LoadThread());
        loadThread.start();
    }

    class LoadThread implements Runnable {
        @Override
        public void run() {
            initData();
        }
    }

    private void initView() {
        this.oneitem_frag_one_ib = (ImageView) getActivity().findViewById(R.id.oneitem_frag_one_ib);
        this.oneitem_frag_one_ib.setOnClickListener(this);
        this.oneitem_frag_two_ib = (ImageView) getActivity().findViewById(R.id.oneitem_frag_two_ib);
        this.oneitem_frag_two_ib.setOnClickListener(this);
        this.oneitem_frag_three_ib = (ImageView) getActivity().findViewById(R.id.oneitem_frag_three_ib);
        this.oneitem_frag_three_ib.setOnClickListener(this);
        this.oneitem_frag_four_ib = (ImageView) getActivity().findViewById(R.id.oneitem_frag_four_ib);
        this.oneitem_frag_four_ib.setOnClickListener(this);
        this.oneitem_frag_one_text = (TextView) getActivity().findViewById(R.id.oneitem_frag_one_text);
        oneitem_frag_one_text.setText("李英然，女，1964年出生，1985年毕业于河北师范大学汉语言文学教育专业，获文学学士学位，现为石家庄学院文学与传媒学院副教授。主要担任中国古代文学、《红楼梦》专题研究等课程。2000年担任古代文学教研室主任至今。2006年担任文传学院中文系党支部书记。做人严谨而不失坦荡，待人宽厚不计较得失。好读书而不求甚解，性超然而敬事以信。最喜欢的格言是“吃亏是福”。喜欢用古体诗抒情言志，对《三国演义》、《红楼梦》等有一定的研究心得。");
        this.oneitem_frag_two_text = (TextView) getActivity().findViewById(R.id.oneitem_frag_two_text);
        this.oneitem_frag_three_text = (TextView) getActivity().findViewById(R.id.oneitem_frag_three_text);
        oneitem_frag_three_text.setText("范川凤，女，1950年出生，河北井陉南张村人。现为石家庄学院文学与传媒学院教授。1988年起开始在报刊杂志发表作品，至今已发表各类体裁文章约300万字。1989年发表第一篇文章《女权主义批评与〈简爱〉》开始关注女性文学的创作与批评，1996年出版论文集《女性批评尝试集》引起省内理论界的重视，开始参与河北省的文学理论工作。2001年出版理论专著《女性文学创作批评》获河北省政府“第九届文艺振兴奖”。2005年出版《美人鱼的渔网从哪能里来——铁凝小说研究》获河北省社科优秀成果奖。论文曾获石家庄市政府“第四届文艺繁荣奖”，“第五届文艺繁荣奖”， “第七届文艺繁荣奖”，华北区文艺理论论文一等奖。河北省文艺理论金鹿奖.");
        this.oneitem_frag_four_text = (TextView) getActivity().findViewById(R.id.oneitem_frag_four_text);
    }

    private void initData() {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request;
        try {
            String SIYUANHUODOND_API_OLD = "http://121.40.142.138:81" + SIYUANHUODOND;
            request = new HttpGet(new URI(SIYUANHUODOND_API_OLD));
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String out = EntityUtils.toString(entity, "UTF-8");
                    try {
                        JSONObject jsonObject = new JSONObject(out);
                        JSONArray eveArray = jsonObject.getJSONArray("content");
                        for (int i = 0; i < 4; i++) {
//                            JSONObject eveobj = eveArray.getJSONObject(i);
//                            String smallPic = getServerURL() + eveobj.getString("smallPic");
//                            Log.d("----------------------------------smallPic", smallPic);
//                            String title = eveobj.getString("title");
                            if (i == 0) {
                                SingleImageTaskUtil imageTask = new SingleImageTaskUtil(
                                        this.oneitem_frag_one_ib);
                                imageTask.execute("http://210.31.249.9/wenchuan/szdwnew/teacherImages/liyingran.JPG");
                            }
                            if (i == 1) {
                                SingleImageTaskUtil imageTask = new SingleImageTaskUtil(
                                        this.oneitem_frag_two_ib);
                                imageTask.execute("http://a3.qpic.cn/psb?/V12cYG6y0mgzhv/F0xmiFqWMXaDvdISQPop8LdUxWtoz91vfxcU4q984Ks!/m/dB4AAAAAAAAAnull&bo=AAQAAgAAAAAFByI!&rf=photolist&t=5");
                            }
                            if (i == 2) {
                                SingleImageTaskUtil imageTask = new SingleImageTaskUtil(
                                        this.oneitem_frag_three_ib);
                                imageTask.execute("http://210.31.249.9/wenchuan/szdwnew/teacherImages/fanchuanfeng.jpg");
                            }
                            if (i == 3) {
                                SingleImageTaskUtil imageTask = new SingleImageTaskUtil(
                                        this.oneitem_frag_four_ib);
                                imageTask.execute("http://a3.qpic.cn/psb?/V12cYG6y0mgzhv/HGl41ocV1xRV98bpMYHU8asYL9rmUaneKYpqWOe*YvQ!/m/dB4AAAAAAAAAnull&bo=igJFAQAAAAAFB.g!&rf=photolist&t=5");
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else {
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.gongzhuo_frag_one_ib:
//                Intent it_gongzhuo = new Intent(getActivity(), GongZhuoActivity.class);
//                startActivity(it_gongzhuo);
//                break;
//            case R.id.gongzhuo_frag_one_ib:
//                Intent it_gongzhuo = new Intent(getActivity(), GongZhuoActivity.class);
//                startActivity(it_gongzhuo);
//                break;
//            case R.id.gongzhuo_frag_one_ib:
//                Intent it_gongzhuo = new Intent(getActivity(), GongZhuoActivity.class);
//                startActivity(it_gongzhuo);
//                break;
//            case R.id.gongzhuo_frag_one_ib:
//                Intent it_gongzhuo = new Intent(getActivity(), GongZhuoActivity.class);
//                startActivity(it_gongzhuo);
//                break;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
