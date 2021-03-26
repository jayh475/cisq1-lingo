<template>

  <div>

    <button  v-on:click="startGame">Start game</button>
    <P>{{ lingoGameData }}</p>

    <input class="form-control" v-model="attempt"  name="guess"  placeholder="guess" >
    <button class="btn" v-on:click="makeGuess" >guess</button>
    <p>Guess is:{{attempt}}</p>
    <p> game Id = {{this.gameId}}</p>

    <div v-if="lingoGameData">
    <div v-for="feedback in lingoGameData.feedbacks" :key="feedback" class="row">
      <div :class="getClassForLetter(feedback.marks[x])" v-for="(letter,x) in feedback.attempt.split('')" :key="letter">{{letter}}</div>
    </div>
    </div>




  </div>


</template>


<script>
import axios from 'axios';
// import lo from 'lodash';


export default {
  name: "Lingo",

  data() {
    return {
      lingoGameData: null,
      attempt: null,
      gameId: null
    }
  },
  methods: {


    startGame() {
      alert("does this work?")


      axios.post(`${this.$restip}/lingoGame/start`).then((response) => {
        this.lingoGameData = response.data;
        this.gameId = response.data.gameId;
        console.log(response.data);


      })
          .catch(error => console.log(error))

    },


    makeGuess() {
      console.log(this.attempt);
      axios.post(`${this.$restip}/lingoGame/${this.gameId}/guess`, {attempt: this.attempt}

      ).then((response) => {
        this.lingoGameData = response.data
        console.log(response);
      })
    },
    getClassForLetter(mark){
      if(mark == "CORRECT"){
        return "box box-correct"
      }
      if(mark == "ABSENT"){
        return "box box-absent"
      }

    }

  }

}

</script>


<style>


.form-control {
  display: inline-block;
}

.btn {
  display: inline-block;
}
.row{
  display: flex;
}

.box{
  border: solid;
}

.box-correct{
  background: green;
}



</style>