package fpt.edu.vn.asfsg1.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.activity.KYCActivity;
import fpt.edu.vn.asfsg1.activity.LoginActivity;
import fpt.edu.vn.asfsg1.activity.PurchaseHistoryActivity;
import fpt.edu.vn.asfsg1.activity.SellerActivity;
import fpt.edu.vn.asfsg1.databinding.FragmentProfileBinding;
import fpt.edu.vn.asfsg1.helper.AuthRepository;
import fpt.edu.vn.asfsg1.helper.UserRepository;
import fpt.edu.vn.asfsg1.models.response.LoginResponse;
import fpt.edu.vn.asfsg1.models.response.UserResponse;
import fpt.edu.vn.asfsg1.services.AuthService;
import fpt.edu.vn.asfsg1.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private AuthService authService;
    private UserService userService;
    TextView fullname, role, toSeller, tvAuth;
    LinearLayout authentication;
    ImageView avatar, iconAuth;
    ConstraintLayout sellerChannel;
    String userObjectString;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout using ViewBinding
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPreferences preferences = requireActivity().getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        userObjectString = preferences.getString("userObject", null);

        authService = AuthRepository.getAuthService();
        userService = UserRepository.getUserService();


        initInfo();
        initButton();
        initLogout();

        return root;
    }

    private void initButton() {
        binding.purchaseHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PurchaseHistoryActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void initInfo() {
        fullname = binding.fullname;
        role = binding.role;
        avatar = binding.pic;
        sellerChannel = binding.sellerChannel;
        authentication = binding.authentication;
        toSeller = binding.tvToSeller;
        iconAuth = binding.iconAuth;
        tvAuth = binding.tvAuth;


        if (userObjectString != null) {
            try {
                JSONObject userObject = new JSONObject(userObjectString);
                String fullName = userObject.getString("fullName");
                String avatar = userObject.getString("avatar");
                String role = userObject.getString("role");

                binding.fullname.setText(fullName);

                Glide.with(binding.pic)
                        .load(avatar)
                        .placeholder(R.drawable.user_default) // Shows default while loading
                        .error(R.drawable.user_default)       // Shows default if URL loading fails
                        .into(binding.pic);

                // Show or hide seller channel based on role
                if ("BUYER".equals(role)) {
                    binding.role.setText("Người mua");
                    toSeller.setText("Trờ thành người bán");
                    Glide.with(iconAuth)
                            .load(R.drawable.not_authorized)
                            .into(iconAuth);

                    tvAuth.setText("Tài khoản chưa xác thực");
                    tvAuth.setTextColor(getResources().getColor(R.color.alert, null));

                    authentication.setVisibility(View.VISIBLE);

                    toSeller.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), KYCActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });

                } else {
                    binding.role.setText("Người bán");
                    binding.sellerChannel.setVisibility(View.VISIBLE);
                    authentication.setVisibility(View.VISIBLE);
                    toSeller.setOnClickListener(v -> {
                        Intent intent = new Intent(getActivity(), SellerActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    });
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void initLogout() {
        TextView logout = binding.tvLogout;

        logout.setOnClickListener(v -> {
            authService.logout();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clear the binding reference to avoid memory leaks
        binding = null;
    }
}
