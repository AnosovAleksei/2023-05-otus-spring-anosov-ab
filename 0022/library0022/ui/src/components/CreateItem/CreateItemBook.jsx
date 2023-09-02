import { useState, useEffect } from "react";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import {Card} from "react-bootstrap";
import Select from "react-select";

export const CreateItemBook = ({placeholder, loadAllData, authorData, genreData, columnNameArray, columnKeyArray, url}) => { 

    console.log("==============>>>>",  authorData, genreData);

    const [dataL, setData] = useState(null);

    const [author, setAuthorData] = useState(null);
    const [genre, setGenreData] = useState(null);

    let optionsAuthor = null;
    
    if(!!authorData && !!authorData.author){
        optionsAuthor = authorData.author.map((item) => ({['value']: item.id, ['label']: item.name}));
    }
    
    let optionsGenre = null;
    
    if(!!genreData && !!genreData.genre){
        optionsGenre = genreData.genre.map((item) => ({['value']: item.id, ['label']: item.name}));
    }
    


    const onSelect = () => {
        
        if(!!dataL){
            const dataforSend = {
               "authorId": author,
               "genreId" : genre,
               "name": dataL.val
            };
                fetch(url,
                                        {
                                            method: "POST", // *GET, POST, PUT, DELETE, etc.
                                            mode: "cors", // no-cors, *cors, same-origin
                                            cache: "no-cache", // *default, no-cache, reload, force-cache, only-if-cached
                                            credentials: "same-origin", // include, *same-origin, omit
                                            headers: {
                                                'Accept': 'application/json',
                                                'Content-Type': 'application/json'
                                            },
                                            redirect: "follow", // manual, *follow, error
                                            referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
                                            body: JSON.stringify(dataforSend), // body data type must match "Content-Type" header
                                        }
                )
                .then((response) => {
                    if (response.ok) {
                      return response.json();
                    }
                    response.json().then(err=>setData({err}))
                  })
                .then(item => setData({item}))
                // .then(()=>setData({ val: null }))
                .then(()=>loadAllData())
                
        }                                
        
    };





    return (<Card>
                {!!optionsAuthor &&
                <Select options={optionsAuthor}
                    placeholder = {"автор"}
                    onChange={opt => setAuthorData(opt.value)}
                    />
                }
                {!!optionsGenre &&
                <Select options={optionsGenre}
                    placeholder = {"жанр"}
                    onChange={opt => setGenreData(opt.value)}
                    />
                }
                <Form.Control
                        placeholder={placeholder}
                        aria-label={placeholder}
                        aria-describedby="basic-addon1"
                        onChange={e => setData({ val: e.target.value })}
                        />
                        <Button variant="primary" onClick={onSelect}>выполнить</Button>

                <table >
                    <thead>
                    <tr>
                        {columnNameArray.map((n, i)=> (<th key={n+i}>{n}</th>))}
                    </tr>
                    </thead>
                    <tbody>
                    { !!dataL && !!dataL.item &&
                        
                            <tr  key={0}>
                                {columnKeyArray.map((k, i)=> (<td key={k+i}>{dataL.item[k]}</td>))}
                            </tr>
                        
                    }
                    </tbody>
                </table>
                {!!dataL && !!dataL.err && !!dataL.err.detail && <h3>{dataL.err.detail}</h3>}
    </Card>)
}
