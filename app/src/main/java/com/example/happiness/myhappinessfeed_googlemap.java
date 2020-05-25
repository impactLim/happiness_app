package com.example.happiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class myhappinessfeed_googlemap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Geocoder geocoder;
    private Button button;
    private EditText editText;

    String str;
    String address;
    LatLng point;
    String latitude;
    String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myhappinessfeed_googlemap);
        editText = (EditText) findViewById(R.id.editText);
        button=(Button)findViewById(R.id.button);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Log.v("구글맵 oncreat 알림","버튼 클릭 알림");

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        geocoder = new Geocoder(this);

        // 초기 좌표값 서울로 지정
        LatLng SEOUL = new LatLng(37.56, 126.97);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

//        // 맵 터치 이벤트 구현 //
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
//            @Override
//            public void onMapClick(LatLng point) {
//                MarkerOptions mOptions = new MarkerOptions();
//                // 마커 타이틀
//                mOptions.title("마커 좌표");
//                Double latitude = point.latitude; // 위도
//                Double longitude = point.longitude; // 경도
//                // 마커의 스니펫(간단한 텍스트) 설정
//                mOptions.snippet(latitude.toString() + ", " + longitude.toString());
//                // LatLng: 위도 경도 쌍을 나타냄
//                mOptions.position(new LatLng(latitude, longitude));
//                // 마커(핀) 추가
//                googleMap.addMarker(mOptions);
//            }
//        });
        ////////////////////

        // 버튼 이벤트
        button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                // String str
                str = editText.getText().toString();
                List<Address> addressList = null;
                try {
                    // editText에 입력한 텍스트(주소, 지역, 장소 등)을 지오 코딩을 이용해 변환
                    addressList = geocoder.getFromLocationName(
                            str, // 주소
                            10); // 최대 검색 결과 개수
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

//                Log.v("구글맵 알림","addressList.get(0).toString() : " + addressList.get(0).toString());

//                System.out.println(addressList.get(0).toString());
                // 콤마를 기준으로 split
                String []splitStr = addressList.get(0).toString().split(",");
                //String address
                address = splitStr[0].substring(splitStr[0].indexOf("\"") + 1,splitStr[0].length() - 2); // 주소

//                System.out.println(address);
                Log.v("구글맵 알림","address : " + address);

                String name = splitStr[1].substring(splitStr[1].indexOf("=") + 1); // feature
                // String latitude
                latitude = splitStr[10].substring(splitStr[10].indexOf("=") + 1); // 위도
                // String longitude
                longitude = splitStr[12].substring(splitStr[12].indexOf("=") + 1); // 경도

                Log.v("구글맵 알림","latitude : " + latitude);
                Log.v("구글맵 알림","longitude : " + longitude);

//                System.out.println(latitude);
//                System.out.println(longitude);

                // 좌표(위도, 경도) 생성
                // LatLng point
                point = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                // 마커 생성
                MarkerOptions mOptions2 = new MarkerOptions();
                mOptions2.title(str);
                mOptions2.snippet(address);
                mOptions2.position(point);

                // 마커 추가
                mMap.addMarker(mOptions2).showInfoWindow();
                // 해당 좌표로 화면 줌
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

            }
        });

        ////////////////////
        // 마커클릭 이벤트 처리
        // GoogleMap 에 마커클릭 이벤트 설정 가능.
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // 마커 클릭시 호출되는 콜백 메서드
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_SHORT).show();
//                https://stackoverflow.com/questions/36497419/open-alertdialog-when-click-on-the-marker-in-google-maps-in-android-studio

                new AlertDialog.Builder(myhappinessfeed_googlemap.this)
                        .setTitle("장소선택").setMessage("이 장소를 선택하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent locationintent = new Intent(myhappinessfeed_googlemap.this, myhappinessfeed_writeActivity.class);

                                locationintent.putExtra("name", str);
                                locationintent.putExtra("address", address);
                                locationintent.putExtra("point", point);
                                locationintent.putExtra("String latitude", latitude);
                                locationintent.putExtra("String longitude", longitude);

                                Log.v("googlemap name, address 확인 알림", "name : " + str + " address : " + address + " point : " + point + " String latitude : " + latitude + " String longitude " + longitude);

                                startActivity(locationintent);

                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        })
                        .show();


                return false;
            }
        });

//        // Add a marker in Sydney and move the camera
//        LatLng basic = new LatLng(37.4856445, 126.972076);
//        mMap.addMarker(new MarkerOptions().position(basic).title("Marker in Korea"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(basic));
    }
}