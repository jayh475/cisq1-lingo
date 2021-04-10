<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;



  color: #2c3e50;
  min-height: 100vh;
}

body, html{
  min-height: 100vh;
  margin: 0;
  font: 400 15px/1.8 "Lato", sans-serif;
  color: #777;
}
</style>


