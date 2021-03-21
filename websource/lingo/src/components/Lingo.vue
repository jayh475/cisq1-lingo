<template>

  <div id="lingo">
    <div v-bind:key="row" v-for="row in splitedList">
      <p>{{ row.name }}</p>
      <div v-bind:key="code" v-for="code in row.codes">
        <p>{{ code }}</p>
      </div>
    </div>
    <button v-on:click="startGame">Start Game</button>

  </div>


</template>


<script>
import axios from 'axios';


export default {
  name: "Lingo",
  data() {
    return {
      lingoGameData: {},
      list: [
        {
          'name': 'Lingo',
          'codes': '111+222+333'
        },

      ]
    }
  },
  computed: {
    splitedList() {
      let newArr = [...this.list]
      newArr.map(el => {
        return el.codes = el.codes.split('+')
      })
      return newArr
    }
  },
  methods: {
    startGame: function () {
      alert("does this work?")


      axios.post(`${this.$restip}/lingoGame/start`).then(function (response) {
        console.log(response);

      })
          .catch(function () {
            alert("dpet het niet")
          });


    }
  }


}

</script>


<style>


</style>