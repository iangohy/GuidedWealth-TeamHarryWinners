// material-ui
// project imports
import { Button, Chip, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TablePagination, TableRow } from '@mui/material';
import { useState, Fragment, useEffect } from 'react';

// firebase setup
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from 'utils/firebase';
import { getFirestore, onSnapshot, collection } from 'firebase/firestore';
import 'firebase/firestore';
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);
// ==============================|| REQUESTS PAGE ||============================== //
const columns = [
    { id: 'name', label: 'Name', minWidth: 10 },
    { id: 'clientType', label: 'Client Type', minWidth: 10 },
    { id: 'bankerAttached', label: 'Banker', minWidth: 10 },
    { id: 'capital', label: 'Capital', minWidth: 10 },
    { id: 'overallPnl', label: 'Overall Profits and Losses', minWidth: 10 },
    { id: 'recurringIncome', label: 'Recurring Income', minWidth: 10 },
    { id: 'totalAssetIncome', label: 'Total Assets', minWidth: 10 },
    // { id: 'totalIncome', label: 'Total Income', minWidth: 10 },
    { id: 'contactNumber', label: 'Contact Number', minWidth: 10 },
    { id: 'telegramHandle', label: 'Telegram Handle', minWidth: 10 }
];

const Requests = () => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(25);
    // firebase for requests
    const [requests, setRequests] = useState([]);
    useEffect(() => {
        setRequests([]);
        const colRef = collection(db, 'banker/Jane Flyer/requests');
        const unsubListener = onSnapshot(colRef, {
            next: (snapshot) => {
                snapshot.docs.map((doc) => {
                    console.log(doc.data());
                    setRequests((requests) => [...requests, doc.data()]);
                });
            },
            error: (err) => {
                // TODO: handle error
            }
        });
        return unsubListener; // <- don't forget to return the unsubscribe function!
    }, []);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    const Row = (props) => {
        const { row } = props;
        return (
            <Fragment>
                <TableRow hover>
                    {columns.map((column) => {
                        if (column.id == 'clientType') {
                            const clientType = row[column.id];
                            return (
                                <TableCell key={column.id} align={column.align}>
                                    <Chip
                                        label={clientType.toUpperCase()}
                                        variant="outlined"
                                        style={{ borderColor: `${clientType}`, backgroundColor: 'white', color: `${clientType}` }}
                                    />
                                </TableCell>
                            );
                        } else {
                            const value = row[column.id];
                            return <TableCell key={column.id}>{value}</TableCell>;
                        }
                    })}
                    <TableCell>
                        <Button href={'/dashboard/default'}>Go to Dashboard</Button>
                    </TableCell>
                </TableRow>
            </Fragment>
        );
    };

    return (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
            <TableContainer sx={{ maxHeight: 1080 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead>
                        <TableRow>
                            {columns.map((column) => {
                                return <TableCell key={column.id}>{column.label}</TableCell>;
                            })}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {requests.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return <Row row={row}></Row>;
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[25, 50, 100]}
                component="div"
                count={requests.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    );
};

export default Requests;
