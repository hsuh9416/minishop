/**
 * 카카오맵 관련 javascript
 */
/*API KAKAO 지도(도메인 등록 후에 사용 가능) */

//1. 맵 컨테이너 작성
var container  = document.getElementById('kakaoMap'), // 이미지 지도를 표시할 div  
    option = { 
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 이미지 지도의 중심좌표
        level: 3, // 이미지 지도의 확대 레벨
    };    
//2. 회사 위치 선택검색
	// 이미지 지도를 생성합니다
var map = new kakao.maps.Map(container, option);

	//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

	//주소로 좌표를 검색합니다
geocoder.addressSearch('서울 종로구 세종로 1-68', function(result, status) {

	  // 정상적으로 검색이 완료됐으면 
    if (status === kakao.maps.services.Status.OK) {

       var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

       // 결과값으로 받은 위치를 마커로 표시합니다
       var marker = new kakao.maps.Marker({
           map: map,
           position: coords
       });

       // 인포윈도우로 장소에 대한 설명을 표시합니다
       var infowindow = new kakao.maps.InfoWindow({
           content: '<div style="width:150px;text-align:center;padding:6px 0;">KISSIN\'BUGS</div>'
       });
       infowindow.open(map, marker);

       // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
       map.setCenter(coords);
   } 
});    

