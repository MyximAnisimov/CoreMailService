<template>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
     <h2>Форма отправки фотографий</h2>
        <input type="file" id="files" ref="files" multiple v-on:change="handleFilesUpload()"/>
    </div>
    <br>
    <div class="large-12 medium-12 small-12 cell">
      <button v-on:click="addFiles()">Выбрать файлы</button>
      <button v-on:click="submitFiles()">Отправить файлы</button></div>
  </div>
</template>

<script>
import jwtDecode from 'jwt-decode';
export default {
  name: 'MainPage',
  props: {
    msg: String
  },
  methods:{
    addFiles(){
      this.$refs.files.click();
    },
    handleFilesUpload(){
      let uploadedFiles = this.$refs.files.files;
      for( var i = 0; i < uploadedFiles.length; i++ ){
        this.files.push( uploadedFiles[i] );
      }
    },
    removeFile( key ){
      this.files.splice( key, 1 );
    },
    submitFiles(){
      let formData = new FormData();
      for( var i = 0; i < this.files.length; i++ ){
        let file = this.files[i];
        formData.append('files', file);
      }
      this.$axios.post( 'http://localhost:8080/api/user/getPhotos',
          formData
      ).then(() => {
        this.$notify({
          group: 'success',
          title: 'Отправка фотографий на сервер',
          text: 'Вы отправили фотографии на сервер',
          type: 'success'
        });
      }).catch(error => {
        this.AxiosErrorHandler(error.response.data);
      });
    },
    AxiosErrorHandler(errorText){
      this.$notify({
        group: 'error',
        title: 'Error',
        text: errorText,
        type: 'error'
      })
    }
  },
  data(){
    return {
      files: []
    }
  },
  mounted() {
    const token = localStorage.getItem('token');

    if (token) {
      const decodedToken = jwtDecode(token);
      this.isModerator = decodedToken.role === 'ROLE_MODERATOR';
    }
  }
}
</script>

<style scoped>
input[type="file"]{
  position: absolute;
  top: -500px;
}
.container {
  position: relative;
  font-size: 20px;
  flex-direction: column;
  margin: 30px auto auto;
  border-radius: 20px;
  background: linear-gradient(to bottom left, rgb(220, 63, 7, 0.8), rgb(130, 30, 145, 0.8));
  padding: 20px;
  display: table;
  text-align: center;
  box-shadow: 0 0 10px 1px black;

}
button {
  text-align: center;
  text-transform: uppercase;
  cursor: pointer;
  letter-spacing: 2px;
  position: relative;
  border: none;
  color: #193047;
  padding: 15px;
  min-width: 150px;
  transition-duration: 0.4s;
  overflow: hidden;
  box-shadow: 0 5px 15px #193047;
  border-radius: 4px;
}
button:hover {
  background: #193047;
  color: black;
}
button::after {
  content: "";
  display: block;
  position: absolute;
  padding-top: 300%;
  padding-left: 350%;
  margin-left: -20px !important;
  margin-top: -120%;
  opacity: 0;
  transition: all 0.8s
}
button:active::after {
  padding: 0;
  margin: 0;
  opacity: 1;
  transition: 0s
}

</style>
