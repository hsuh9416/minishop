/**
 * ckeditor4 설정
 */

    $(function(){
         
        CKEDITOR.replace( 'review_content', {//해당 이름으로 된 textarea에 에디터를 적용
            width:'100%',
            height:'450px',
            filebrowserImageUploadUrl: '${pageContext.request.contextPath}/reviewImgUpload.do', //여기 경로로 파일을 전달하여 업로드 시킨다.
            
        });
               
        CKEDITOR.on('dialogDefinition', function( ev ){
            var dialogName = ev.data.name;
            var dialogDefinition = ev.data.definition;
          
            switch (dialogName) {
                case 'image': //Image Properties dialog
                    //dialogDefinition.removeContents('info');
                    dialogDefinition.removeContents('Link');
                    dialogDefinition.removeContents('advanced');
                    break;
            }
        });
         
    });