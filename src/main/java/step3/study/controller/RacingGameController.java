package step3.study.controller;

import step3.study.domain.Drivers;
import step3.study.domain.Round;
import step3.study.dto.RequestRacingDTO;
import step3.study.dto.ResponseRacingDTO;
import step3.study.dto.ResponseWinnerDTO;
import step3.study.util.RandomGenerator;
import step3.study.util.StringUtils;
import step3.study.util.Validator;
import step3.study.view.InputView;
import step3.study.view.ResultView;

import java.util.*;

public class RacingGameController {
    private final InputView inputView;
    private final ResultView resultView;
    private final Scanner scanner;

    public RacingGameController() {
        inputView = new InputView();
        resultView = new ResultView();
        scanner = new Scanner(System.in);
    }

    public void run() {
        RequestRacingDTO requestRacingDTO = exportRequestDtoFromInputView();

        Drivers drivers = new Drivers(Drivers.of(requestRacingDTO));

        startRacingGameForRound(requestRacingDTO, drivers);
    }

    private void startRacingGameForRound(RequestRacingDTO requestRacingDTO, Drivers drivers) {

        Round round = requestRacingDTO.getRound();
        while (round.racing()) {
            drivers.moveCars(new RandomGenerator(new Random()));
            ResponseRacingDTO responseRacingDTO = exportResponseRacingDto(drivers);
            printRacingGameResult(responseRacingDTO);
            round.next();
        }
        printRacingGameWinners(exportResponseRacingDto(drivers));
    }

    private void printRacingGameWinners(ResponseRacingDTO responseRacingDTO) {
        Drivers drivers = responseRacingDTO.getDrivers();
        ResponseWinnerDTO responseWinnerDTO = new ResponseWinnerDTO(drivers.getWinnerNames());
        resultView.racingGameWinners(responseWinnerDTO);
    }

    private void printRacingGameResult(ResponseRacingDTO responseRacingDTO) {
        resultView.print(responseRacingDTO);
    }

    public ResponseRacingDTO exportResponseRacingDto(Drivers drivers) {
        return new ResponseRacingDTO(drivers);
    }

    public RequestRacingDTO exportRequestDtoFromInputView() {
        List<String> driverNames = gerDriverNames();
        Round round = new Round(inputView.inputRound(scanner));

        return new RequestRacingDTO(driverNames, round);
    }

    private List<String> gerDriverNames() {
        String driverName = inputView.inputDriverNames(scanner);

        if (Validator.isEmpty(driverName)) {
            throw new IllegalArgumentException("빈 문자열 입니다.");
        }
        return new ArrayList<>(Arrays.asList(StringUtils.split(driverName, ",")));
    }
}
