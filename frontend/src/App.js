import { Routes, Route } from "react-router-dom";
import Footer from "./wireframe/Footer";
import Header from "./wireframe/Header";
import Main from "./components/Main";
import MySurveys from './components/MySurveys';
import EditSurvey from './components/EditSurvey';
import Vote from './components/Vote';
import Survey from './components/Survey'

function App() {
  return (
    <div className="App">
      <Header />
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/main" element={<Main />} />
        <Route path="/mysurveys" element={<MySurveys />} />
        <Route path="/survey" element={<Survey />} />
        <Route path="/editsurvey" element={<EditSurvey />} />
        <Route path="/vote" element={<Vote />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
