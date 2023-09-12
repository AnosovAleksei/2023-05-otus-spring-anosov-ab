import { useState, useEffect } from "react";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import {Card} from "react-bootstrap";

export const CreateItem = ({placeholder, loadAllData, columnNameArray, columnKeyArray, url}) => { 

    const [dataL, setData] = useState(null);


    
    const onSelect = () => {
        if(!!dataL){
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
                                            body: JSON.stringify({"name": dataL.val}), // body data type must match "Content-Type" header
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
