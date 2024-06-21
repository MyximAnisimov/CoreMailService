<template>
  <div id="app">
    <Loader id="load" v-if="loading"/>
    <div id="contentContainer" v-else>
      <router-view/>
      <notifications group="error" position="bottom right" max="5" />
      <notifications group="success" position="bottom right" max="5" />
    </div>

  </div>
</template>

<script>

export default {
  name: 'App',
  mounted(){
    setTimeout(() => {this.loading = false}, 1000)
  }
}
</script>

<style>
body{
  margin: 0;
  padding: 0;
  height: 100%;
  background-color: whitesmoke;
}

#contentContainer{
  margin-top: 100px;
  margin-bottom: 90px;
}
#load{
  position: absolute;
  top: 50%;
  left: 50%;
  margin-right: -50%;
  transform: translate(-50%, -50%);
  color: grey;
}
</style>
