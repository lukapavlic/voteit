import React from 'react'
import Survey from './Survey'

const Surveys = ({surveys}) => {
    return (
        <>
            {surveys.map((s) =>
            (<Survey key={s.id} survey={s}/>)

            )}

        </>
    )
}

export default Surveys
