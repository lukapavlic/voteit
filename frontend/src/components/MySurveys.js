import Survey from './Survey'

const MySurveys = ({surveys}) => {
    return (
        <>
            { surveys.map((s) => (
                
                <Survey key={s.id} survey={s} />
            
            ))}
        </>
    )
}

export default MySurveys
