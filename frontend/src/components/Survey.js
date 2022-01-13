const Survey = ({survey}) => {
    return (
        <div >
            <h3>{survey.name}</h3>
            { survey.options.map((o) => (
                <div key={o.id}>
                    {o.value}
                    <br />
                </div>
            ))}
        </div>
    )
}

export default Survey
