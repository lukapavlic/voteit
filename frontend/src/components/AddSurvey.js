import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { useSearchParams } from "react-router-dom"

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
            <div>
                Survey name:
                <input type='text' value={name} onChange={(e) => { setName(e.target.value) }} />
                <br />
                Survey type:
                <select value={type} onChange={(e) => {setType(e.target.value)}}>
                    <option value="SELECT_ONE">Select one option</option>
                    <option value="RANK_ALL">Rank all options</option>
                </select>
                <br />

                <input type='checkbox' value={enabled} onChange={(e) => { setEnabled(e.target.checked) }} />
                Enabled
                <br />

                <input type='checkbox' value={resultsPubliclyAvailable} onChange={(e) => { setResultsPubliclyAvailable(e.target.checked) }} />
                Results will be publicly available
                <br />

                <input type='checkbox' value={voteCanBeAltered} onChange={(e) => { setVoteCanBeAltered(e.target.checked) }} />
                Voters can update their choice several times
                <br />

                <br />Options: <button onClick={addOption}>+</button> <br />
                {options.map((o, i) => (
                    <div key={i}>
                        <input  type='text' value={o.value} onChange={(e) => {
                            options[i].value = e.target.value
                            setOptions([...options])
                        }} />
                        <button onClick={(e)=>deleteOption(e,o.orderValue)}>-</button>
                    </div>
                ))}
                
            </div>
            <button onClick={addSurvey}>Add Survey</button>

        </form>
    )
}

export default AddSurvey
