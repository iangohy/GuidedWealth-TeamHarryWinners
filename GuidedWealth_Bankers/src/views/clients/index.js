// material-ui
// project imports
import { Button, Chip, IconButton } from '@mui/material';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { useEffect, useState } from 'react';
import { Fragment } from 'react';

// firebase setup
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from 'utils/firebase';
import { getFirestore, onSnapshot, collection } from 'firebase/firestore';
import 'firebase/firestore';
import { useNavigate } from 'react-router';
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

// ==============================|| CLIENTS PAGE ||============================== //
// TODO: replace with firebase call
const columns = [
    { id: 'name', label: 'Name', minWidth: 10 },
    { id: 'clientType', label: 'Client Type', minWidth: 10 },
    { id: 'bankerAttached', label: 'Banker', minWidth: 10 },
    { id: 'capital', label: 'Capital', minWidth: 10 },
    { id: 'overallPnl', label: 'Overall Profits and Losses', minWidth: 10 },
    { id: 'recurringIncome', label: 'Recurring Income', minWidth: 10 },
    { id: 'totalAsset', label: 'Total Assets', minWidth: 10 },
    { id: 'contactNumber', label: 'Contact Number', minWidth: 10 },
    { id: 'telegramHandle', label: 'Telegram Handle', minWidth: 10 }
];

const Clients = () => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(25);
    const navigate = useNavigate();

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    // firebase for clients
    const [clients, setClients] = useState([]);
    useEffect(() => {
        setClients([]);
        var clientsTemp = [];
        const colRef = collection(db, 'client');
        const unsubListener = onSnapshot(colRef, {
            next: (snapshot) => {
                snapshot.docs.map((doc) => {
                    clientsTemp = clientsTemp.concat(doc.data());
                    setClients((clients) => [...clients, doc.data()]);
                });
            },
            error: (err) => {
                // TODO: handle error
            }
        });
        return unsubListener; // <- don't forget to return the unsubscribe function!
    }, []);
    console.log(clients);
    // // firebase for single user chad
    // useEffect(() => {
    //     setHoldings([]);
    //     var holdingTemp = [];
    //     const colRef = collection(db, `users/chad/holdings`);
    //     const unsubListener = onSnapshot(colRef, {
    //         next: (snapshot) => {
    //             snapshot.docs.map((doc) => {
    //                 holdingTemp = holdingTemp.concat(doc.data());
    //                 setHoldings((holdings) => [...holdings, doc.data()]);
    //             });
    //         },
    //         error: (err) => {
    //             // TODO: handle error
    //         }
    //     });
    //     return unsubListener; // <- don't forget to return the unsubscribe function!
    // }, []);
    // console.log(holdings);
    // console.log(userHoldings);

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
                            const value = row[column.id] != null && row[column.id] != '' ? row[column.id] : '-';
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
                                return (
                                    <TableCell key={column.id} align={column.align}>
                                        {column.label}
                                    </TableCell>
                                );
                            })}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {clients.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return <Row row={row}></Row>;
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[25, 50, 100]}
                component="div"
                count={clients.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    );
};

export default Clients;
