import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { Button, TextField } from '@mui/material';

const Main = () => {

    const [newSurveyName, setNewSurveyName] = useState('')
    const [tokenToVote, setTokenToVote] = useState('')
    let navigate = useNavigate();

    return (
        <div>

            <div>
                <TextField label="New Survey Name" variant="outlined" size="small" onChange={(e) => { setNewSurveyName(e.target.value) }} />
                <Button variant='contained' onClick={(e) => {
                    navigate('/editsurvey?newSurveyName=' + newSurveyName)
                }}>Create It!</Button>
            </div>

            <div>
                <TextField label="Your Token" variant="outlined" size="small" onChange={(e) => { setTokenToVote(e.target.value) }} />
                <Button variant='contained' onClick={(e) => {
                    navigate('/vote?token=' + tokenToVote)
                }} >Vote It!</Button>
            </div>

        </div>
    )
}

export default Main