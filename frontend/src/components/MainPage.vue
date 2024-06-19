<template>
  <div class="container">
    <div class="large-12 medium-12 small-12 cell">
      <label>Files
        <input type="file" id="files" ref="files" multiple v-on:change="handleFilesUpload()"/>
      </label>
    </div>
<!--    <div class="large-12 medium-12 small-12 cell">-->
<!--      <div v-for="(file, key) in files" class="file-listing">{{ file.name }} <span class="remove-file" v-on:click="removeFile( key )">Remove</span></div>-->
<!--    </div>-->
    <br>
    <div class="large-12 medium-12 small-12 cell">
      <button v-on:click="addFiles()">Add Files</button>
    </div>
    <br>
    <div class="large-12 medium-12 small-12 cell">
      <button v-on:click="submitFiles()">Submit</button>
    </div>
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
          title: 'Отправка запроса по авторизации',
          text: 'Вы можете отправвлять запросы',
          type: 'success'
        });
      })
    }
  },
  data(){
    return {
      files: []
    }
  },
  mounted() {
    // Получение токена из localStorage (или другого места)
    const token = localStorage.getItem('token');

    // Декодирование токена
    if (token) {
      const decodedToken = jwtDecode(token);
      this.isModerator = decodedToken.role === 'ROLE_MODERATOR'; // Добавьте поле "role" в payload токена
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
input[type="file"]{
  position: absolute;
  top: -500px;
}
div.file-listing{
  width: 200px;
}
span.remove-file{
  color: red;
  cursor: pointer;
  float: right;
}
</style>
