import Surveys from "./components/Surveys";
import { useState } from 'react'

function App() {

  const [surveys, setSurveys] = useState([
    {
      id: 1,
      title: 'Survey1'
    },
    {
      id: 2,
      title: 'Survey2'
    }
  ])

  return (
    <div className="App">
      <h1>Vote IT!</h1>
      Many options.<br/>
      One person - One vote. Anonymous. Period.<br/>
      No login required! *<br/><br/>

      * For voting.
      <hr/>
      Create a Survey | Enter your token to Vote!
      <hr/>

      <Surveys surveys={surveys} />

    </div>
  );
}

export default App;
