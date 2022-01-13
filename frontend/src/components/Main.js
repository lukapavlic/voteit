import { useState } from "react"
import { useNavigate } from "react-router-dom"

const Main = () => {

    const [newSurveyName, setNewSurveyName] = useState('')
    const [tokenToVote, setTokenToVote] = useState('')
    let navigate = useNavigate();

    return (
        <div>
            Many options.
            One person - One vote. Anonymous. Period.<br />

            <table>
                <tbody><tr>
                    <td>
                        Create a Survey:<br/>
                        <input type='text' onChange={(e) => { setNewSurveyName(e.target.value) }} /><br/>
                        <button onClick={(e) => {
                            navigate('/addsurvey?name='+newSurveyName)
                        }} >Create It!</button>
                    </td>
                    <td>
                        &nbsp;
                    </td>
                    <td>
                        Insert your token to vote:<br/>
                        <input type='text' onChange={(e) => { setTokenToVote(e.target.value) }} /><br/>
                        <button onClick={(e) => {
                            navigate('/vote?name='+tokenToVote)
                        }} >Vote It!</button>
                    </td>
                    </tr></tbody>
            </table>

        </div>
    )
}

export default Main