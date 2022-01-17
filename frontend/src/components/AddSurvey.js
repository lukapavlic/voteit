import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { useSearchParams } from "react-router-dom"
import {Button,Grid,MenuItem,Select,SvgIcon,Switch,TextField} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';

const AddSurvey = ({addSurveyFunction}) => {

    let [searchParams, setSearchParams] = useSearchParams();
    let navigate = useNavigate();
    
    const [name, setName] = useState('')
    const [type, setType] = useState('SELECT_ONE')
    const [enabled, setEnabled] = useState(false)
    const [resultsPubliclyAvailable, setResultsPubliclyAvailable] = useState(true)
    const [voteCanBeAltered, setVoteCanBeAltered] = useState(true)

    const [options, setOptions] = useState([{ orderValue: 1, value: 'Option 1' }, { orderValue: 2, value: 'Option 2' }])

    const addOption = (e) => {
        console.log('addOption')
        e.preventDefault()
        let newUid = 0
        options.map((o) => {
            if (newUid <= o.orderValue) newUid = o.orderValue + 1
        })
        const newOpt={ orderValue: newUid, value: '' }
        setOptions([...options,newOpt])
    }

    const deleteOption = (e,uid) => {
        console.log('deleteOption')
        e.preventDefault()
        setOptions(options.filter((o) => o.orderValue !== uid))
    }

    const addSurvey = (e) => {
        console.log('addSurvey')
        e.preventDefault()

        const surveyToAdd=
            {
                "name": name,
                "type": type,
                "options": options,
                "resultsPubliclyAvailable": resultsPubliclyAvailable,
                "voteCanBeAltered": voteCanBeAltered,
                "enabled": enabled
            }

        addSurveyFunction(surveyToAdd)
        navigate('/mysurveys')
        
    }

    useEffect(() => {
        const n = searchParams.get('name')
        if (n !== null) setName(n)
    }, [])

    return (
        <form>
            <h3>Add New Survey</h3>


<Grid container spacing='2'>

    <Grid item xs='3'>Survey name:</Grid>
    <Grid item xs='9'><TextField label="New Survey Title" variant="outlined" size="small" value={name} onChange={(e) => { setName(e.target.value) }} /></Grid>

    <Grid item xs='3'>Survey type:</Grid>
    <Grid item xs='9'>
        <Select size="small" value={type} label="Survey type" onChange={(e) => {setType(e.target.value)}} >
            <MenuItem value="SELECT_ONE">Select one option</MenuItem>
            <MenuItem value="RANK_ALL">Rank all options</MenuItem>
        </Select>
    </Grid>

    <Grid item xs='3'>Enabled</Grid>
    <Grid item xs='9'><Switch  checked={enabled} onChange={(e) => { setEnabled(e.target.checked) }} /></Grid>

    <Grid item xs='3'>Results will be publicly available</Grid>
    <Grid item xs='9'><Switch checked={resultsPubliclyAvailable} onChange={(e) => { setResultsPubliclyAvailable(e.target.checked) }} /></Grid>

    <Grid item xs='3'>Voters can update their choice several times</Grid>
    <Grid item xs='9'><Switch checked={voteCanBeAltered} onChange={(e) => { setVoteCanBeAltered(e.target.checked) }} /></Grid>

    <Grid item xs='3'>
        Options:<br/>
        <Button variant='outlined' size="small" onClick={addOption}>+</Button>
    </Grid>
    <Grid item xs='9'>
         <br />
        {options.map((o, i) => (
            <div key={i}>
                <TextField label="New Option Title" variant="outlined" size='small' value={o.value} onChange={(e) => {
                    options[i].value = e.target.value
                    setOptions([...options])
                }} />
                <Button variant='outlined' size="small" onClick={(e)=>deleteOption(e,o.orderValue)}>-</Button>
            </div>
        ))}
    </Grid>

    <Grid item xs='3'><Button variant='contained' onClick={addSurvey}>Add Survey</Button></Grid>
    <Grid item xs='9'></Grid>
</Grid>

            

        </form>
    )
}

export default AddSurvey
