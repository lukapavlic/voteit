import { useState, useEffect } from 'react'
import { useSearchParams } from "react-router-dom"
import { Button, TextField } from '@mui/material';
import * as React from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useNavigate } from "react-router-dom"

const Survey = () => {

    const [survey, setSurvey] = useState({ name: '', options: [] })
    let [urlParams, setUrlParams] = useSearchParams();

    let navigate = useNavigate();

    useEffect(() => {
        const load = async () => {
            const fromServer = await getData()
            setSurvey(fromServer)
        }
        load()
    }, []
    )

    const getData = async () => {
        const res = await fetch(process.env.REACT_APP_SURVEYS_BACKEND + '/' + urlParams.get('id'))
        const data = await res.json()
        return data
    }

    const deleteData = async () => {
        await fetch(
            process.env.REACT_APP_SURVEYS_BACKEND + '/' + survey.id,
            { method: 'DELETE', 'Content-Type': 'application/json' }
        )
        //setSurveys(surveys.filter((s) => s.id !== id)
        //)
    }

    const [openDeleteDialog, setOpenDeleteDialog] = React.useState(false);

    const handleClickDeleteSurvey = () => {
        setOpenDeleteDialog(true);
    };

    const handleDeleteDialogClose = () => {
        setOpenDeleteDialog(false);
    };

    const handleYesDelete = () => {
        setOpenDeleteDialog(false);
        deleteData()
        navigate('/mysurveys')
    };

    return (
        <div >
            <h3>{survey.name} [{survey.type}]</h3>

            Created {survey.timeCreated} (Modified {survey.timeLastModified}) <br />
            {(survey.enabled) ? "Voting is enabled" : "Voting is disabled"} <br />
            {(survey.resultsPubliclyAvailable) ? "Public results" : "Private results"} <br />
            {(survey.voteCanBeAltered) ? "Vote can be changed" : "Vote if final"} <br />

            <ul>
                {survey.options.sort((o1, o2) => { return o1.orderValue - o2.orderValue }).map((o) => (
                    <li key={o.id}>
                        {o.value}
                        <br />
                    </li>
                ))}
            </ul>

            Edit | Enable/Disable | Manage voting tokens | View Results

            <Button variant='contained' onClick={handleClickDeleteSurvey}>Delete this Survey</Button>
            <Dialog
                open={openDeleteDialog}
                onClose={handleDeleteDialogClose}
                aria-labelledby="alert-dialog-title"
                aria-describedby="alert-dialog-description">
                <DialogTitle id="alert-dialog-title">Are you sure to delete this Survey?</DialogTitle>
                <DialogContent><DialogContentText id="alert-dialog-description">
                    Deleting the survey will delete all the results as well. Please consider disabling the survey as oposed to deleting it.
                </DialogContentText></DialogContent>
                <DialogActions>
                    <Button onClick={handleDeleteDialogClose} autoFocus>No, do not delete it.</Button>
                    <Button onClick={handleYesDelete}>I am sure - delete the survey</Button>
                </DialogActions>
            </Dialog>

        </div>
    )
}

export default Survey




