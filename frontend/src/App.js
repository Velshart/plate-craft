import './App.css';
import Button from "@mui/material/Button";
import {Checkbox, FormControl, FormControlLabel, FormGroup, InputLabel, MenuItem, Select} from "@mui/material";
import {useEffect, useState} from "react";

function App() {
    const [selectedPlate, setSelectedPlate] = useState('');
    const [selectedLetter, setSelectedLetter] = useState('');
    const [error, setError] = useState('');
    const [apiData, setApiData] = useState([]);
    const [selectedContent, setSelectedContent] = useState('');
    const [generatedPlateInputText, setGeneratedPlateInputText] = useState('Generated plate:');

    const handlePlateChange = (plate) => {
        setSelectedPlate(plate);
    };

    const handleLetterChange = (event) => {
        setSelectedLetter(event.target.value);
    };


    const handleGenerateButtonClick = () => {
        if (!selectedLetter || !selectedPlate) {
            setError('Some options are missing!');
        } else {
            fetchApiData();
            setError('');
        }

    };

    const handleContentChange = (event) => {
        setSelectedContent(event.target.value);
    };

    const fetchApiData = () => {
        fetch('http://localhost:8081/api/' + selectedPlate + '/' + selectedLetter)
            .then(response => response.json())
            .then(data => setApiData(data))
            .catch(error => {
                console.error("An error occurred during fetching data", error);
            });
    }

    const prefixes: string[] = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');


    return (
        <div className="App">
            <h2 style={{textTransform: "uppercase"}} className="App-header">
                <p>
                    Plate Craft
                </p>

                <FormControl className={"prefixes-form"}>
                    <InputLabel id="select-label" className={"prefixes-form-input-label"} sx={{color: 'white'}}>
                        select a custom license plate prefix used in your Voivodeship
                    </InputLabel>

                    <Select
                        labelId="select-label"
                        value={selectedLetter}
                        onChange={handleLetterChange}
                    >
                        {prefixes.map((prefix) => (
                            <MenuItem key={prefix} value={prefix}>
                                {prefix}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                <FormGroup row={true}>
                    <FormControlLabel control={<Checkbox checked={selectedPlate === 'polish'}
                                                         onChange={() => handlePlateChange('polish')}
                    />
                    }
                                      label="Polish plates" sx={{mr: 20}}
                    />

                    <FormControlLabel control={<Checkbox checked={selectedPlate === 'english'}
                                                         onChange={() => handlePlateChange('english')}
                    />
                    }
                                      label="English plates"
                    />
                </FormGroup>

                <div className="generate-button">
                    <Button variant="contained" onClick={handleGenerateButtonClick}>
                        Generate
                    </Button>
                </div>

                <FormControl className={"generated-plate-input-form"}>
                    <InputLabel id="content-select-label">Here are some plates you may like</InputLabel>
                    <Select
                        labelId="content-select-label"
                        value={selectedContent}
                        onChange={handleContentChange}
                    >
                        {apiData.map((item, index) => (
                            <MenuItem disabled={true} key={index} value={item.content}>
                                {item.content}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                {error && <p className="error-message">{error}</p>}
            </h2>
            <footer className="App-footer">
                <a href="https://github.com/Velshart/plate-craft" target="_blank" rel="noopener noreferrer">
                    Source code can be found here
                </a>
            </footer>
        </div>
    );
}

export default App;
