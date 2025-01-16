package fpt.edu.vn.asfsg1.ui.auction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import fpt.edu.vn.asfsg1.R;
import fpt.edu.vn.asfsg1.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    // Sử dụng ViewBinding (nếu bạn muốn), ở đây ta vừa giữ reference binding
    // vừa khai báo TextView thủ công để minh họa

    // private FragmentDetailBinding binding; // Nếu bạn muốn dùng ViewBinding

    // Các TextView hiển thị theo spec mới
    private TextView tvCpu, tvRam, tvScreenSize, tvCameraSpecs, tvConnectivity, tvSensors;
    private TextView tvSim, tvSimSlots, tvOs, tvOsFamily, tvBluetooth, tvUsb, tvWlan, tvSpeed, tvNetworkTech;

    private TextView tvBatteryHealth, tvImei, tvStorage, tvBodyCondition, tvScreenCondition;
    private TextView tvCameraCondition, tvPortCondition, tvButtonCondition, tvBrand, tvModel;
    private TextView tvSerial, tvControlNumber, tvManufacturer;
    private ImageView ivDeviceImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout mới đã cập nhật
        // Nếu dùng ViewBinding, ta làm: binding = FragmentDetailBinding.inflate(inflater, container, false);
        // return binding.getRoot();
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Nếu không dùng ViewBinding, ta findViewById theo id TextView
        tvCpu = view.findViewById(R.id.tvCpu);
        tvRam = view.findViewById(R.id.tvRam);
        tvScreenSize = view.findViewById(R.id.tvScreenSize);
        tvCameraSpecs = view.findViewById(R.id.tvCameraSpecs);
        tvConnectivity = view.findViewById(R.id.tvConnectivity);
        tvSensors = view.findViewById(R.id.tvSensors);
        tvSim = view.findViewById(R.id.tvSim);
        tvSimSlots = view.findViewById(R.id.tvSimSlots);
        tvOs = view.findViewById(R.id.tvOs);
        tvOsFamily = view.findViewById(R.id.tvOsFamily);
        tvBluetooth = view.findViewById(R.id.tvBluetooth);
        tvUsb = view.findViewById(R.id.tvUsb);
        tvWlan = view.findViewById(R.id.tvWlan);
        tvSpeed = view.findViewById(R.id.tvSpeed);
        tvNetworkTech = view.findViewById(R.id.tvNetworkTechnology);

        // Ánh xạ các trường còn lại:
        tvBatteryHealth = view.findViewById(R.id.tvBatteryHealth);
        tvImei = view.findViewById(R.id.tvImei);
        tvStorage = view.findViewById(R.id.tvStorage);
        tvBodyCondition = view.findViewById(R.id.tvBodyCondition);
        tvScreenCondition = view.findViewById(R.id.tvScreenCondition);
        tvCameraCondition = view.findViewById(R.id.tvCameraCondition);
        tvPortCondition = view.findViewById(R.id.tvPortCondition);
        tvButtonCondition = view.findViewById(R.id.tvButtonCondition);
        tvBrand = view.findViewById(R.id.tvBrand);
        tvModel = view.findViewById(R.id.tvModel);
        tvSerial = view.findViewById(R.id.tvSerial);
        tvControlNumber = view.findViewById(R.id.tvControlNumber);
        tvManufacturer = view.findViewById(R.id.tvManufacturer);
        ivDeviceImage = view.findViewById(R.id.ivDeviceImage);

        if (getArguments() != null) {
            String imageUrl = getArguments().getString("device_image", "");
            // Các trường đã có:
            tvCpu.setText(getArguments().getString("cpu", "N/A"));
            tvRam.setText(getArguments().getString("ram", "N/A"));
            tvScreenSize.setText(getArguments().getString("screen_size", "N/A"));
            tvCameraSpecs.setText(getArguments().getString("camera_specs", "N/A"));
            tvConnectivity.setText(getArguments().getString("connectivity", "N/A"));
            tvSensors.setText(getArguments().getString("sensors", "N/A"));
            tvSim.setText(getArguments().getString("sim", "N/A"));
            tvSimSlots.setText(getArguments().getString("sim_slots", "N/A"));
            tvOs.setText(getArguments().getString("os", "N/A"));
            tvOsFamily.setText(getArguments().getString("os_family", "N/A"));
            tvBluetooth.setText(getArguments().getString("bluetooth", "N/A"));
            tvUsb.setText(getArguments().getString("usb", "N/A"));
            tvWlan.setText(getArguments().getString("wlan", "N/A"));
            tvSpeed.setText(getArguments().getString("speed", "N/A"));
            tvNetworkTech.setText(getArguments().getString("network_technology", "N/A"));

            // Set giá trị cho các trường còn lại:
            tvBatteryHealth.setText(getArguments().getString("battery_health", "N/A"));
            tvImei.setText(getArguments().getString("imei", "N/A"));
            tvStorage.setText(getArguments().getString("storage", "N/A"));
            tvBodyCondition.setText(getArguments().getString("body_condition", "N/A"));
            tvScreenCondition.setText(getArguments().getString("screen_condition", "N/A"));
            tvCameraCondition.setText(getArguments().getString("camera_condition", "N/A"));
            tvPortCondition.setText(getArguments().getString("port_condition", "N/A"));
            tvButtonCondition.setText(getArguments().getString("button_condition", "N/A"));
            tvBrand.setText(getArguments().getString("brand", "N/A"));
            tvModel.setText(getArguments().getString("model", "N/A"));
            tvSerial.setText(getArguments().getString("serial", "N/A"));
            tvControlNumber.setText(getArguments().getString("control_number", "N/A"));
            tvManufacturer.setText(getArguments().getString("manufacturer", "N/A"));
            if (!imageUrl.isEmpty()) {
                Glide.with(this)
                        .load(imageUrl)
                        .placeholder(R.drawable.cat1) // Ảnh placeholder trong khi tải
                        .error(R.drawable.warning)       // Ảnh lỗi khi tải thất bại
                        .into(ivDeviceImage);
            }
        }
    }
}
