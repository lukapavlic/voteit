import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import Divider from '@mui/material/Divider';
import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"

const MySurveys = () => {

    let navigate = useNavigate();

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
        const res = await fetch(process.env.REACT_APP_SURVEYS_BACKEND)
        const data = await res.json()
        return data
    }

    return (
        <List component="nav" aria-label="mailbox folders">

            {surveys.map((s) => (
                <>
                    <ListItem key={s.id} button onClick={(e) => navigate('/survey?id=' + s.id)} >
                        <ListItemText primary={s.name} secondary={s.timeCreated} />
                    </ListItem>
                    <Divider />
                </>
            ))}

        </List>
    )
}

export default MySurveys







