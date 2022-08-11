import { Link } from 'react-router-dom';

// material-ui
import { ButtonBase } from '@mui/material';

// project imports
import config from 'config';
import Logo from '../../../assets/images/logo.png';

// ==============================|| MAIN LOGO ||============================== //

const LogoSection = () => (
    <ButtonBase disableRipple component={Link} to={config.defaultPath}>
        <img style={{ aspectRatio: '370/90', width: 200 }} alt="logo" src={Logo}></img>
    </ButtonBase>
);

export default LogoSection;
