// material-ui
// project imports
import { IconButton } from '@mui/material';
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
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import TextField from '@material-ui/core/TextField';
import Modal from '@mui/material/Modal';
import { makeStyles } from '@material-ui/core/styles';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import RadioGroup from '@mui/material/RadioGroup';
import Radio from '@mui/material/Radio';
import Button from '@mui/material/Button';

// firebase setup
import { initializeApp } from 'firebase/app';
import { firebaseConfig } from 'utils/firebase';
import { getFirestore, onSnapshot, collection, query, getDocs, updateDoc, where, doc } from 'firebase/firestore';
import 'firebase/firestore';
const app = initializeApp(firebaseConfig);
const db = getFirestore(app);

// ==============================|| StockTable PAGE ||============================== //
// TODO: replace with firebase call
const columns = [
    { id: 'description', label: 'Description', minWidth: 10 },
    { id: 'currentYield', label: 'Current Yield', minWidth: 10 },
    { id: 'delta', label: 'Delta', minWidth: 10 },
    { id: 'quantity', label: 'Quantity', minWidth: 10 },
    { id: 'symbol', label: 'Symbol', minWidth: 10 },
    { id: 'unitCost', label: 'Unit Cost', minWidth: 10 },
    { id: 'unrealisedPnl', label: 'Unrealised Profits and Losses', minWidth: 10 }
];

const StockTable = () => {
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(25);

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    // firebase call for users collection
    const [docs, setDocs] = useState();
    const [holdings, setHoldings] = useState([]);
    const [userHoldings, setUserHoldings] = useState([]);
    const [users, setUsers] = useState(['chad', 'e']);

    // firebase for single user chad
    useEffect(() => {
        setHoldings([]);
        var holdingTemp = [];
        const colRef = collection(db, `client/chad/holdings`);
        const unsubListener = onSnapshot(colRef, {
            next: (snapshot) => {
                snapshot.docs.map((doc) => {
                    holdingTemp = holdingTemp.concat(doc.data());
                    setHoldings((holdings) => [...holdings, doc.data()]);
                });
            },
            error: () => {
                // TODO: handle error
            }
        });
        return unsubListener; // <- don't forget to return the unsubscribe function!
    }, []);

    function getModalStyle() {
        const top = 50;
        const left = 50;

        return {
            top: `${top}%`,
            left: `${left}%`,
            transform: `translate(-${top}%, -${left}%)`
        };
    }

    // ==============================|| ROW PAGE ||============================== //

    const Row = (props) => {
        const { row } = props;
        const [open, setOpen] = useState(false);
        const [whichopen, setwhichopen] = useState('');

        const handleOpen = (e) => {
            setOpen(true);
            console.log('opening:' + e);
            setwhichopen(e);
        };

        const handleClose = () => {
            setOpen(false);
        };

        const useStyles = makeStyles((theme) => ({
            paper: {
                position: 'absolute',
                width: 400,
                backgroundColor: theme.palette.background.paper,
                boxShadow: theme.shadows[5],
                padding: theme.spacing(4),
                outline: 'none'
            }
        }));

        const classes = useStyles();
        const [modalStyle] = useState(getModalStyle);

        const defaultValues = {
            input: '',
            buy_sell: ''
        };

        const [formValues, setFormValues] = useState(defaultValues);

        const handleInputChange = (e) => {
            const { name, value } = e.target;
            setFormValues({
                ...formValues,
                [name]: value
            });
            console.log(formValues);
        };

        const updateUser = async () => {
            console.log(whichopen);
            const userRef = query(collection(db, 'client/chad/holdings'), where('symbol', '==', whichopen));
            console.log('Setting:' + formValues['input'] + formValues['buy_sell']);
            const findUsers = await getDocs(userRef);
            findUsers.forEach(async (user) => {
                const getUser = doc(db, 'client/chad/holdings', user.id);
                await updateDoc(getUser, {
                    input: formValues['input'],
                    action: formValues['buy_sell']
                });
            });
        };

        const handleSubmit = (event) => {
            event.preventDefault();
            console.log(formValues['input']);
            updateUser();
        };

        const body = (
            <div style={modalStyle} className={classes.paper}>
                <form>
                    <Paper>
                        {/* <TextField
                            id="input"
                            name="input"
                            label="Quantity"
                            variant="filled"
                            multiline
                            minRows={4}
                            inputProps={{
                                inputMode: 'numeric',
                                pattern: '/^-?d+(?:.d+)?$/g'
                            }}
                            value={formValues.input}
                            onChange={handleInputChange}
                        /> */}
                        <label htmlFor="input">Quantity </label>
                        <input
                            id="input"
                            name="input"
                            type="number"
                            label="Quantity"
                            pattern="[0-9]*"
                            value={formValues.input}
                            onChange={handleInputChange}
                        />
                        <FormControl>
                            {/* <FormLabel>Gender</FormLabel> */}
                            <RadioGroup name="buy_sell" value={formValues.buy_sell} onChange={handleInputChange} row>
                                <FormControlLabel key="BUY" value="BUY" control={<Radio size="small" />} label="BUY" />
                                <FormControlLabel key="SELL" value="SELL" control={<Radio size="small" />} label="SELL" />
                                <FormControlLabel key="other" value="other" control={<Radio size="small" />} label="Other" />
                            </RadioGroup>
                            <Button onClick={handleSubmit}>Save</Button>
                        </FormControl>
                    </Paper>
                </form>
            </div>
        );

        return (
            <Fragment>
                <TableRow hover>
                    {columns.map((column) => {
                        const col = column.id;
                        return (
                            <TableCell key={column.id} align={column.align}>
                                {row[col]}
                            </TableCell>
                        );
                    })}
                    <TableCell>
                        <IconButton aria-label="expand row" size="small" onClick={() => handleOpen(row.symbol)}>
                            {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
                        </IconButton>
                    </TableCell>
                    <Modal open={open} onClose={handleClose}>
                        {body}
                    </Modal>
                </TableRow>
            </Fragment>
        );
    };

    // ==============================|| Table ||============================== //

    return (
        <Paper sx={{ width: '100%', overflow: 'hidden' }}>
            <TableContainer component={Paper}>
                <Table aria-label="collapsible table">
                    <TableHead>
                        <TableRow>
                            {columns.map((column) => {
                                if (column.id != 'customerId') {
                                    return (
                                        <TableCell key={column.id} align={column.align}>
                                            {column.label}
                                        </TableCell>
                                    );
                                }
                            })}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {holdings.map((holding) => (
                            <Row key={holding.description} row={holding} />
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[25, 50, 100]}
                component="div"
                count={holdings.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    );
};

export default StockTable;
