import { useState, useEffect } from 'react'
import { Routes, Route, Link } from "react-router-dom";
import Footer from "./wireframe/Footer";
import Header from "./wireframe/Header";
import Main from "./components/Main";
import MySurveys from './components/MySurveys';
import AddSurvey from './components/AddSurvey';
import Vote from './components/Vote';

function App() {

  const [surveys, setSurveys] = useState([])

  useEffect(() => {
    const load = async () => {
      const fromServer = await getData()
      setSurveys(fromServer)
    }
    load()
    }, []
  )

  const getData = async () => {
    const res = await fetch('http://127.0.0.1:8888/api/v1/surveys')
    const data = await res.json()
    return data
  }

  const postData = async (survey) => {
    console.log('postData: '+survey)
    console.log('RAW: '+JSON.stringify(survey))
    const res = await fetch(
      'http://127.0.0.1:8888/api/v1/surveys',
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(survey)
      }
    )
    const data = await res.json
    setSurveys([...surveys, data])
  }

  const deleteData = async (id) => {
    await fetch(
      'http://127.0.0.1:8888/api/v1/surveys/${id}',
      { method: 'DELETE' }
    )
    setSurveys(surveys.filter((s) => s.id !== id)
    )
  }

  return (
      <div className="App">
        <Header/>
        <Routes>
          <Route path="/" element={<Main/>} />
          <Route path="/main" element={<Main/>} />
          <Route path="/mysurveys" element={<MySurveys surveys={surveys} />} />
          <Route path="/addsurvey" element={<AddSurvey addSurveyFunction={postData}/>} />
          <Route path="/vote" element={<Vote />} />
        </Routes>
        <Footer/>
      </div>
  );
}

export default App;
