package com.wonders.health.venus.open.user.module.home.registration;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wonders.health.venus.open.user.BaseActivity;
import com.wonders.health.venus.open.user.R;
import com.wonders.health.venus.open.user.entity.ContactStatus;
import com.wonders.health.venus.open.user.entity.ContactVO;
import com.wonders.health.venus.open.user.util.Constant;
import com.wondersgroup.hs.healthcloud.common.util.StringUtil;
import com.wondersgroup.hs.healthcloud.common.util.UIUtil;
import com.wondersgroup.hs.healthcloud.common.view.TitleBar;

/**
 * Created by sunning on 16/1/4.
 * 增删改查 就诊人
 */
public class RegistrationContactActivity extends BaseActivity {

    private ContactStatus contactStatus;

    private LinearLayout mRegistration_contact_detail_root;
    private TextView mRegistration_contact_sex;
    private TextView mRegistration_contact_age;
    private EditText mRegistration_contact_name;
    private EditText mRegistration_contact_idCard;
    private EditText mRegistration_contact_phoneNO;
    private View mRegistration_contact_line;
//    private ToggleButton mRegistration_contact_isDefault;

    private String contactId;

    private int defaultCode;


    @Override
    protected void initViews() {
        setContentView(R.layout.add_contact_layout);
        mRegistration_contact_detail_root = (LinearLayout) findViewById(R.id.registration_contact_detail_root);
        mRegistration_contact_sex = (TextView) findViewById(R.id.registration_contact_sex);
        mRegistration_contact_age = (TextView) findViewById(R.id.registration_contact_age);
        mRegistration_contact_name = (EditText) findViewById(R.id.registration_contact_name);
        mRegistration_contact_idCard = (EditText) findViewById(R.id.registration_contact_idCard);
        mRegistration_contact_phoneNO = (EditText) findViewById(R.id.registration_contact_phoneNO);
        mRegistration_contact_line = findViewById(R.id.registration_contact_line);
//        mRegistration_contact_isDefault = (ToggleButton) findViewById(R.id.registration_contact_isDefault);
//
//        mRegistration_contact_isDefault.setOnToggleChanged(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private void loadContactDetail(int type) {
        if (!TextUtils.isEmpty(contactId)) {
//            RegistrationManager.getInstance().queryContactDetail(contactId, new FinalResponseCallback<ContactVO>(this, type) {
//                @Override
//                public void onSuccess(ContactVO contactVO) {
//                    super.onSuccess(contactVO);
//                    defaultCode = contactVO.getIsDefault();
//                    initTitle();
//                    bindView(contactVO);
//                }
//            });
        } else {
            initTitle();
        }
    }

    private void bindView(ContactVO contactVO) {
        if (!TextUtils.isEmpty(contactVO.getName())) {
            mRegistration_contact_name.setText(contactVO.getName());
        }
        if (contactVO.getAge() != 0) {
            mRegistration_contact_age.setText(String.valueOf(contactVO.getAge()));
        }
        if (!TextUtils.isEmpty(contactVO.getIdcard())) {

            mRegistration_contact_idCard.setText(StringUtil.decodeIdCardByNum(contactVO.getIdcard(),4,4));
        }
        if (!TextUtils.isEmpty(contactVO.getGender())) {
            mRegistration_contact_sex.setText(contactVO.getGender());
        }
        if (!TextUtils.isEmpty(contactVO.getMobile())) {
            mRegistration_contact_phoneNO.setText(contactVO.getMobile());
        }
//        if (contactVO.getIsDefault() == 1) {
//            mRegistration_contact_isDefault.setToggleOn();
//            mRegistration_contact_isDefault.setClickable(false);
//        } else {
//            mRegistration_contact_isDefault.setToggleOff();
//        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadParams();
        loadContactDetail(Constant.TYPE_INIT);
    }

    private void loadParams() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            contactStatus = (ContactStatus) bundle.getSerializable(ContactListActivity.CONTACT_STATUS_FLAG);
            contactId = bundle.getString(ContactListActivity.CONTACT_ID);
        }
        if (contactStatus == null) {
            contactStatus = ContactStatus.CREATE;
        }
        optionLayoutByContactStatus();
    }

    private void optionLayoutByContactStatus() {
        switch (contactStatus) {
            case RETRIEVE:
                mRegistration_contact_detail_root.setVisibility(View.VISIBLE);
                mRegistration_contact_line.setVisibility(View.GONE);
                mRegistration_contact_name.setKeyListener(null);
                mRegistration_contact_idCard.setKeyListener(null);
                mRegistration_contact_phoneNO.setKeyListener(null);
                break;
        }
    }

    private void initTitle() {
        mTitleBar.setTitle(contactStatus.getTitle());
        mTitleBar.addAction(new TitleBar.TextAction(contactStatus.getRightBtnText()) {
            @Override
            public void performAction(View view) {
                switch (contactStatus) {
                    case CREATE:
                        UIUtil.hideSoftInput(RegistrationContactActivity.this);
                        submit();
                        break;
                    case RETRIEVE:
                        if (defaultCode == 1) {
                            UIUtil.toastShort(RegistrationContactActivity.this, "默认联系人不能删除");
                        } else {
                            UIUtil.showConfirm(RegistrationContactActivity.this, "提示", "是否删除当前联系人", "删除", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    delete();
                                }
                            });
                        }
                        break;
                }
            }
        });
    }

    private void delete() {
//        RegistrationManager.getInstance().deleteContact(contactId, new ResponseCallback<String>() {
//
//            @Override
//            public void onStart() {
//                super.onStart();
//                UIUtil.showProgressBar(RegistrationContactActivity.this);
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                UIUtil.hideProgressBar(RegistrationContactActivity.this);
//                if (mSuccess) {
//                    RegistrationContactActivity.this.finish();
//                }
//            }
//        });
    }

    private void submit() {
//        ContactVO vo = checkoutUserInput();
//        if (vo != null) {
//            RegistrationManager.getInstance().addContact(vo, new ResponseCallback<String>() {
//                @Override
//                public void onStart() {
//                    super.onStart();
//                    UIUtil.showProgressBar(RegistrationContactActivity.this);
//                }
//
//                @Override
//                public void onSuccess(String t) {
//                    super.onSuccess(t);
//                    String name = mRegistration_contact_name.getText().toString();
//                    JSONObject json = JSON.parseObject(t);
//                    String contactID = json.getString("id");
//                    EventBus.getDefault().post(new ContactEvent(name, contactID));
//                    finish();
//                }
//
//                @Override
//                public void onFailure(HttpException error, String msg) {
//                    super.onFailure(error, msg);
//                }
//
//                @Override
//                public void onFinish() {
//                    super.onFinish();
//                    if (!isFinishing()) {
//                        UIUtil.hideProgressBar(RegistrationContactActivity.this);
//                    }
//                }
//            });
//        }
    }

//    private ContactVO checkoutUserInput() {
//        String idCard = mRegistration_conta/**/ct_idCard.getText().toString().trim();
//        String phone = mRegistration_contact_phoneNO.getText().toString().trim();
//        String name = mRegistration_contact_name.getText().toString().trim();
//        if (TextUtils.isEmpty(idCard)) {
//            UIUtil.toastShort(this, "身份证号不能为空");
//            return null;
//        }
//        if (TextUtils.isEmpty(phone)) {
//            UIUtil.toastShort(this, "手机号不能为空");
//            return null;
//        }
//        if (TextUtils.isEmpty(name)) {
//            UIUtil.toastShort(this, "姓名不能为空");
//            return null;
//        }
//        return new ContactVO(name, idCard, phone, mRegistration_contact_isDefault.isToggleOn() ? 1 : 0);
//    }

//    @Override
//    public void onToggle(boolean on) {
//        if (contactStatus != ContactStatus.CREATE) {  //不是新建 需要调接口
//            if (on) {
//                mRegistration_contact_isDefault.setToggleOff();
//                setDefault();
//            }
//        }
//    }

    private void setDefault() {
//        RegistrationManager.getInstance().setContactDefault(contactId, new ResponseCallback<String>() {
//            @Override
//            public void onStart() {
//                super.onStart();
//                UIUtil.showProgressBar(RegistrationContactActivity.this);
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                super.onSuccess(s);
//                UIUtil.toastShort(RegistrationContactActivity.this, s);
//                defaultCode = 1;
//            }
//
//            @Override
//            public void onFinish() {
//                super.onFinish();
//                UIUtil.hideProgressBar(RegistrationContactActivity.this);
//                if (mSuccess) {
//                    mRegistration_contact_isDefault.setClickable(false);
//                    mRegistration_contact_isDefault.setToggleOn(true);
//                }
//            }
//        });
    }

    @Override
    protected boolean needCheckLogin() {
        return true;
    }
}
