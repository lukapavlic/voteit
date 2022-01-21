import { useState, useEffect } from 'react'
import { useSearchParams } from "react-router-dom"

const Vote = () => {
    const [survey, setSurvey] = useState({ name: '', options: [] })
    let [urlParams, setUrlParams] = useSearchParams();

    useEffect(() => {
        const load = async () => {
            const fromServer = await getData()
            setSurvey(fromServer)
        }
        load()
    }, []
    )

    const getData = async () => {
        const res = await fetch(process.env.REACT_APP_VOTE_BACKEND + '/' + urlParams.get('token'))
        const data = await res.json()
        return data
    }
    return (
        <div >
            <h3>{survey.name} [{survey.type}]</h3>

            Created {survey.timeCreated} (Modified {survey.timeLastModified}) <br/>
            {(survey.enabled)?"Voting is enabled":"Voting is disabled"} <br/>
            {(survey.resultsPubliclyAvailable)?"Public results":"Private results"} <br/>
            {(survey.voteCanBeAltered)?"Vote can be changed":"Vote if final"} <br/>

            <ul>
                {survey.options.sort((o1, o2) => { return o1.orderValue - o2.orderValue }).map((o) => (
                    <li key={o.id}>
                        {o.value}
                        <br />
                    </li>
                ))}
            </ul>

            Vote | Update Vote | Voting is disabled | Results |
        </div>
    )
}

export default Vote