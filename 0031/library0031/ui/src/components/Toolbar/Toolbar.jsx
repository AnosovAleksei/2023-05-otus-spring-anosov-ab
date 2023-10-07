import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Container, Row, Col} from 'react-bootstrap';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import "./Toolbar.css";


import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from 'react-router-dom';

import {
  Author,
  Book,
  Genre,
  Commentary
} from "../../scenes";
// import { Row } from 'react-bootstrap';

export function Toolbar() {
  const pathBase = "/";
  const pathBook = "/book";
  const pathAuthor = "/author";
  const pathGenre = "/genre";
  const pathCommentary = "/commentary";
  


  return (
    <>
        <Navbar expand="lg" className="bg-body-tertiary" fixed="left">
            <Container fluid>
              <Navbar.Brand href={pathBase} >BookService</Navbar.Brand>
              <Navbar.Toggle aria-controls="basic-navbar-nav" />
              <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="me-auto">
                  <Nav.Link href={pathBook}>book</Nav.Link>
                  <Nav.Link href={pathAuthor}>author</Nav.Link>
                  <Nav.Link href={pathGenre}>genre</Nav.Link>
                  <Nav.Link href={pathCommentary}>commentary</Nav.Link>
                </Nav>
              </Navbar.Collapse>
            </Container>
          </Navbar>
          <Router>
              <Routes>
                  <Route path={pathBook} element={<Book />} />
                  <Route path={pathAuthor} element={<Author />} />
                  <Route path={pathGenre} element={<Genre />} />
                  <Route path={pathCommentary} element={<Commentary />} />
                  <Route path={pathBase} element={<Navigate replace to={pathBook} />} />
              </Routes>
          </Router>
          
    </>    
  );
}

