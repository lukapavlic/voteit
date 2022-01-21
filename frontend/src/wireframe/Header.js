import { Routes, Route, Link } from "react-router-dom";

const Header = () => {

    return (
        <>
            <h1>Vote IT!</h1>

            <Link to="/main">Home</Link> |
            <Link to="/mysurveys">My Surveys</Link> |
            <Link to="/editsurvey?newSurveyName=Survey">Add New Survey</Link>

            <hr />
        </>
    )
}

export default Header
