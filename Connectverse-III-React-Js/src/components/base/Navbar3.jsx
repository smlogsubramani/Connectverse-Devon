import { Navbar, Container, Nav } from "react-bootstrap";
import { NavLink } from "react-router-dom";

import { getUserId, handleLogout } from "../../utils/auth";

const Navigation = () => {
    return (
        <Navbar
            collapseOnSelect
            sticky="top"
            expand="lg"
            bg="light"
            variant="light"
        >
            <Container>
                <Navbar.Brand href="/">
                    <h5>Connectverse</h5>
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">
                        {/* <Nav.Link href="#features">Features</Nav.Link>
            <Nav.Link href="#pricing">Pricing</Nav.Link>
            <NavDropdown title="Dropdown" id="collasible-nav-dropdown">
              <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
              <NavDropdown.Item href="#action/3.2">Another action</NavDropdown.Item>
              <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
              <NavDropdown.Divider />
              <NavDropdown.Item href="#action/3.4">Separated link</NavDropdown.Item>
            </NavDropdown> */}
                    </Nav>
                    <Nav>
                        {/* <Nav.Link href="#deets">More deets</Nav.Link>
            <Nav.Link eventKey={2} href="#memes">
              Dank memes
            </Nav.Link> */}
                        <NavLink to="/projects">
                            <h3>Projects</h3>
                        </NavLink>&nbsp;
                        <NavLink to="/jobs">
                            <h3>Jobs</h3>
                        </NavLink>&nbsp;
                        <NavLink to="/courses">
                            <h3>Courses</h3>
                        </NavLink>&nbsp;
                        <NavLink to="/companies">
                            <h3>Companies</h3>
                        </NavLink>&nbsp;

                        {
                            getUserId() ?
                                <Nav.Link onClick={handleLogout}>
                                    <h3>Logout</h3>
                                </Nav.Link> :
                                <NavLink to="/login">
                                    <h3>login</h3>
                                </NavLink>
                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
};

export default Navigation;
